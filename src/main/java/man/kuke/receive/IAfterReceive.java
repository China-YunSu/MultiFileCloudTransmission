package man.kuke.receive;

import man.kuke.core.Resource;

/**
 * @author: kuke
 * @date: 2021/2/4 - 0:09
 * @description:
 */
public interface IAfterReceive {
    void afterReceive(ResourceRepository repository);
    void afterReceive();
}
