package man.kuke.registry;

import man.kuke.core.NetNode;
import man.kuke.registry.mapper.ConsumerService;
import man.kuke.spring.ioc.AutoWired;
import man.kuke.spring.ioc.Component;

import java.util.Set;

/**
 * @author: kuke
 * @date: 2021/2/2 - 17:22
 * @description:
 */
@Component
public class ConsumeHandler implements ConsumerService {
    @AutoWired
    private ServiceProvider serviceProvider;

    public void setServiceprovider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public Set<NetNode> getServiceAddress(String service) {
        return serviceProvider.getServiceAddress(service);
    }

}
