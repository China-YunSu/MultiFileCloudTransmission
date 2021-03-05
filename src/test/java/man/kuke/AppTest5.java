package man.kuke;

import man.kuke.core.NetNode;
import man.kuke.core.Resource;
import man.kuke.registry.mapper.ConsumerService;
import man.kuke.rmi.core.RMIClient;
import man.kuke.rmi.core.RMIServer;
import man.kuke.sender.mapper.ResourcesServices;

import java.util.Set;

/**
 * @author: kuke
 * @date: 2021/2/3 - 13:47
 * @description:
 */
public class AppTest5 {
    public static void main(String[] args) {
        RMIClient registry = new RMIClient();
        registry.setPort(54100);
        registry.setIp("127.0.0.1");
        ConsumerService registryProxy = (ConsumerService) registry.getProxy(ConsumerService.class);
        Set<NetNode> kuke = registryProxy.getServiceAddress("kuke");
        System.out.println(kuke);

        RMIClient serviceServer = new RMIClient();
        serviceServer.setPort(50000);
        serviceServer.setIp("127.0.0.1");
        ResourcesServices resources = (ResourcesServices) serviceServer.getProxy(ResourcesServices.class);
        Resource res = resources.getResource("kuke");
        System.out.println(res);
    }
}
