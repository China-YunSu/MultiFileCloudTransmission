package man.kuke.receive;

import man.kuke.core.NetNode;

import java.util.List;
import java.util.Set;

/**
 * @author: kuke
 * @date: 2021/2/4 - 17:34
 * @description:
 */
public interface ISenderSelectedStrategy {
    List<NetNode> selectedSender(Set<NetNode> senderList);
}
