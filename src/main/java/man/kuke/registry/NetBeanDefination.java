package man.kuke.registry;

import man.kuke.core.NetNode;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: kuke
 * @date: 2021/2/2 - 16:46
 * @description:
 */
public class NetBeanDefination extends NetNode {
    private Set<String> services;

    public NetBeanDefination(String ip, int port) {
        super(ip, port);
        services = ConcurrentHashMap.newKeySet();
    }

    public void addService(String service) {
        services.add(service);
    }

    public void removeService(String service) {
        services.remove(service);
    }

    public Set<String> getServices() {
        return services;
    }

}
