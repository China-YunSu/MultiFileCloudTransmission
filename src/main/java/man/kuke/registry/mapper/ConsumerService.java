package man.kuke.registry.mapper;

import man.kuke.core.NetNode;

import java.util.Set;

/**
 * @author: kuke
 * @date: 2021/2/2 - 17:23
 * @description:
 */
public interface ConsumerService {
    Set<NetNode> getServiceAddress(String service);
}
