package man.kuke.receive;

import man.kuke.core.DataHeader;
import man.kuke.core.FileSplit;
import man.kuke.core.NetNode;
import man.kuke.core.Resource;
import man.kuke.sender.ResourceHolder;
import man.kuke.sender.ServerConversation;
import man.kuke.spring.ioc.BeanFactory;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: kuke
 * @date: 2021/2/2 - 10:30
 * @description:
 */
public class Client {
    private NetNode serverNode;
    private NetNode me;
    private ISenderSelectedStrategy senderSelectedStrategy;
    private ResourceHolder resourceHolder;
    private ClientConversation clientConversation;
    private ServerConversation serverConversation;

    public Client(NetNode serverNode, NetNode centerRegistry,
                  NetNode centerRequest,int receivePort,int serverPort) {
        try {
            BeanFactory.scannPackage("man.kuke.sender");
            resourceHolder = (ResourceHolder) BeanFactory.getBean(ResourceHolder.class);
            this.serverNode = serverNode;
            me = new NetNode(InetAddress.getLocalHost().getHostAddress()
                    , receivePort);
            NetNode selfServer = new NetNode(me.getIp(), serverPort);
            serverConversation = new ServerConversation(centerRegistry,selfServer);
            clientConversation  = new ClientConversation(centerRequest,me);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setSenderSelectedStrategy(ISenderSelectedStrategy senderSelectedStrategy) {
        this.senderSelectedStrategy = senderSelectedStrategy;
    }

    public void getResource(String resourceName, String resourcePath) throws Exception {
        //获取资源信息
        Resource resource = clientConversation.getResourceInfo(resourceName, serverNode);
        resource.setPath(resourcePath);

        //连接注册中心请求服务地址列表
        List<NetNode> serviceAddress = getServiceAddress(resourceName);

        //开启仓库
        ResourceRepository resourceRepository = new ResourceRepository(resource,
                me.getPort(), new Handler());

        //建立资源接收列表
        List<List<DataHeader>> dataList = resourceRepository.getData(serviceAddress.size());

        //资源发送请求
        clientConversation.requestResource(resourceName,serviceAddress,dataList);
    }

    private List<NetNode> getServiceAddress(String resourceName) {
        Set<NetNode> serviceAddress = clientConversation.getServiceAddress(resourceName);

        // 负载均衡
        if (senderSelectedStrategy == null) {
            senderSelectedStrategy = new SenderSelect();
        }

        return senderSelectedStrategy.selectedSender(serviceAddress);
    }

    private class Handler extends AfterReceiveAdpter {

        @Override
        public void afterReceive(ResourceRepository repository) {
            Resource resource = repository.getResource();
            if (repository.isAllReceived()) {
                //资源注册
                resourceHolder.addResource(resource);
                if (!serverConversation.isOpen()) {
                    serverConversation.connectToRegistryCentor();
                    serverConversation.openServer();
                }
                serverConversation.register(resource.getResourceName());
            } else {
                //断点续传：对端异常

                //获取服务地址
                List<NetNode> serviceAddress = getServiceAddress(resource.getResourceName());

                //建立资源接收列表
                List<List<DataHeader>> dataList = repository.getLostData(2);
                //资源发送请求
                clientConversation.requestResource(resource.getResourceName(),
                        serviceAddress,dataList);
            }
        }

    }

    private static class SenderSelect implements ISenderSelectedStrategy{

        @Override
        public List<NetNode> selectedSender(Set<NetNode> senderList) {
            List<NetNode> netNodes = new ArrayList<>(senderList);
            for (NetNode netNode : senderList) {
                if (netNode.getHealth() > 5) {
                    netNodes.add(netNode);
                }
            }
            return netNodes;
        }
    }

}
