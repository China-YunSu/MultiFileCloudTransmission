package man.kuke.sender;

import man.kuke.core.DataHeader;
import man.kuke.core.NetNode;
import man.kuke.core.Resource;
import man.kuke.core.ResourceFactory;
import man.kuke.sender.mapper.ResourcesServices;
import man.kuke.spring.ioc.AutoWired;
import man.kuke.spring.ioc.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


/**
 * @author: kuke
 * @date: 2021/2/3 - 18:55
 * @description:
 */

@Component
public class ResourceHolder implements ResourcesServices {
    @AutoWired
    private ResourcePool resourcePool;

    private ServerConversation serverConversation;

    public void setServerConversation(ServerConversation serverConversation) {
        this.serverConversation = serverConversation;
    }

    public void setResourcepool(ResourcePool resourcePool) {
        this.resourcePool = resourcePool;
    }

    @Override
    public void resourceRequest(String resourceName, List<DataHeader> dataHeaders, NetNode requester) {
        try {
            System.out.println("requester " + requester.getIp() + " " + requester.getPort());
            ResourceSender resourceSender = new ResourceSender(getResource(resourceName),new IAfterSendAction(){
                @Override
                public void afterSend() {
                    serverConversation.reportHealth(1);
                }
            });
            resourceSender.setIp(requester.getIp());
            resourceSender.setPort(requester.getPort());
            System.out.println("正在传送");
            resourceSender.send(dataHeaders);
            serverConversation.reportHealth(-1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resource getResource(String resourceName) {
        return resourcePool.getResource(resourceName);
    }

    public void addLocalResource(String resourceName, String resourcePath) throws FileNotFoundException {
        Resource resource = ResourceFactory.createResource(resourceName, resourcePath);
        resourcePool.addResource(resource);
    }

    public void addResource(Resource resource) {
        resourcePool.addResource(resource);
    }

}
