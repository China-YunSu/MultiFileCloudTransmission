package man.kuke.receive;

import man.kuke.core.DataHeader;

import java.io.IOException;

/**
 * @author: kuke
 * @date: 2021/1/28 - 23:23
 * @description:
 */
public interface DataProcesser {
    long dataProcesser(DataHeader dataHeader, byte[] data) throws IOException;
}
