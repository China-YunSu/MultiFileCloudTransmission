package man.kuke.registry;

import man.kuke.nio.core.IServerAction;
import man.kuke.spring.ioc.AutoWired;
import man.kuke.spring.ioc.Component;

/**
 * @author: kuke
 * @date: 2021/2/3 - 10:14
 * @description:
 */
@Component
public class PeerDownHandler implements IServerAction {
    @AutoWired
    private ServiceProvider serviceProvider;

    public void setServiceprovider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void dealPeerDown(String id, String ip, int port) {
        serviceProvider.logout(ip, port);
    }

    @Override
    public void dealOffline(String id, String ip, int port) {
        serviceProvider.logout(ip, port);
    }
}
