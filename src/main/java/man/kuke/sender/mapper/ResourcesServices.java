package man.kuke.sender.mapper;

import man.kuke.core.DataHeader;
import man.kuke.core.NetNode;
import man.kuke.core.Resource;

import java.util.List;

/**
 * @author: kuke
 * @date: 2021/1/30 - 21:27
 * @description:
 */
public interface ResourcesServices {
    void resourceRequest(String resourceName,List<DataHeader> dataHeaders, NetNode requester);
    Resource getResource(String resourceName);
}
