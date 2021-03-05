package man.kuke.sender;

import man.kuke.core.Resource;
import man.kuke.spring.ioc.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: kuke
 * @date: 2021/2/3 - 19:10
 * @description:
 */
@Component
public class ResourcePool {
    private Map<String, Resource> resources;

    public ResourcePool() {
        resources = new HashMap<>();
    }

    public void addResource(Resource resource) {
        if (!resources.containsKey(resource.getResourceName())) {
            resources.put(resource.getResourceName(), resource);
        }
    }

    public void removeResource(Resource resource) {
        resources.remove(resource.getResourceName());
    }

    public Resource getResource(String resourceName) {
        return resources.get(resourceName);
    }


}
