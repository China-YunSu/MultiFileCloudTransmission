package man.kuke.registry;

import man.kuke.core.NetNode;
import man.kuke.spring.ioc.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: kuke
 * @date: 2021/2/2 - 16:28
 * @description:
 */
@Component
public class ServiceProvider {
    private final Map<String, Set<NetNode>> registrationForm;
    private final Map<String, NetBeanDefination> provider;

    public ServiceProvider() {
        registrationForm = new ConcurrentHashMap<>();
        provider = new ConcurrentHashMap<>();
    }
    
    public NetBeanDefination getNetBeanDefination(String ip,int port) {
        NetBeanDefination netBeanDefination = provider.get(ip);
        if (netBeanDefination == null) {
            synchronized (provider) {
                netBeanDefination = provider.get(ip);
                if (netBeanDefination == null) {
                    netBeanDefination = new NetBeanDefination(ip,port);
                    provider.put(ip, netBeanDefination);
                }
            }
        }
        
        return netBeanDefination;
    }

    public Set<NetNode> getServiceAddress(String service) {
        Set<NetNode> netNodes = registrationForm.get(service);

        if (netNodes == null) {
            synchronized (registrationForm) {
                netNodes = registrationForm.get(service);
                if (netNodes == null) {
                    netNodes = ConcurrentHashMap.newKeySet();
                    registrationForm.put(service,netNodes);
                }
            }
        }

        return netNodes;
    }

    public NetBeanDefination removeNetBeanDefination(String ip) {
        return provider.remove(ip);
    }

    public void removeAddresses(Set<String> services) {
        for (String service : services) {
            registrationForm.remove(service);
        }
    }

    public void registerService(String ip, int port, String service) {
        NetBeanDefination netBeanDefination = getNetBeanDefination(ip, port);
        netBeanDefination.addService(service);

        Set<NetNode> serviceAddress = getServiceAddress(service);
        serviceAddress.add(netBeanDefination);
    }

    public void logoutService(String ip, int port, String service) {
        NetBeanDefination netBeanDefination = getNetBeanDefination(ip, port);
        netBeanDefination.removeService(service);

        Set<NetNode> serviceAddress = getServiceAddress(service);
        serviceAddress.remove(netBeanDefination);
    }

    public void reportHealth(String ip, int port, int health) {
        NetBeanDefination netBeanDefination = getNetBeanDefination(ip, port);
        netBeanDefination.setHealth(health);
    }

    public void logout(String ip, int port) {
        NetBeanDefination netBeanDefination = removeNetBeanDefination(ip);
        if (netBeanDefination != null) {
            removeAddresses(netBeanDefination.getServices());
        }
    }

}
