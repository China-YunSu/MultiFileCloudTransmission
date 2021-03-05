package man.kuke.receive;

import man.kuke.core.DataHeader;
import man.kuke.core.NetNode;
import man.kuke.core.Resource;
import man.kuke.sender.mapper.ResourcesServices;
import man.kuke.registry.mapper.ConsumerService;
import man.kuke.rmi.core.RMIClient;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

/**
 * @author: kuke
 * @date: 2021/1/30 - 20:58
 * @description:
 */
public class ClientConversation {
    private RMIClient resourceRmiClient;
    private ResourcesServices resourcesServer;
    private RMIClient registryRmiClient;
    private ConsumerService registryCentor;
    private NetNode me;

    public ClientConversation(NetNode centerNode, NetNode me) {
        this.me = me;
        resourceRmiClient = new RMIClient();
        registryRmiClient = new RMIClient();
        registryRmiClient.setIp(centerNode.getIp());
        registryRmiClient.setPort(centerNode.getPort());
        registryCentor = (ConsumerService) registryRmiClient.getProxy(ConsumerService.class);
        resourcesServer = (ResourcesServices) resourceRmiClient.getProxy(ResourcesServices.class);
    }

    public Set<NetNode> getServiceAddress (String service) {
        return registryCentor.getServiceAddress(service);
    }

    public void setRegistryPort(int port) {
        registryRmiClient.setPort(port);
    }

    public void setRegistryIp(String ip) {
        registryRmiClient.setIp(ip);
    }

    public void requestResource(String resourceName,List<NetNode> servers, List<List<DataHeader>> dataInfo) {
        int index = 0;
        for (NetNode netNode : servers) {
            resourceRmiClient.setIp(netNode.getIp());
            resourceRmiClient.setPort(netNode.getPort());
            resourcesServer.resourceRequest(resourceName, dataInfo.get(index++),me);
            System.out.println("请求完毕");
        }
    }

    public Resource getResourceInfo(String resourceName, NetNode serverNode) throws Exception {
        resourceRmiClient.setIp(serverNode.getIp());
        resourceRmiClient.setPort(serverNode.getPort());
        Resource resource = resourcesServer.getResource(resourceName);

        if (resource == null) {
            throw new Exception("资源为空，获取资源信息失败");
        }

        return resource;
    }
}
