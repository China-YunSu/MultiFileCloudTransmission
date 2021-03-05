package man.kuke.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: kuke
 * @date: 2021/1/28 - 11:42
 * @description: 针对网络底层数据传输
 */
public abstract class NetTransfer {

    /**
     *
     * @param out 输出流
     * @param data 数据
     * @throws IOException
     * 这里存在IP分包问题，32KB分包
     */
    public static void out(OutputStream out, byte[] data) throws IOException {
        out.write(data);
    }

    /**
     *
     * @param in 输入流
     * @param length 数据长度
     * @return 读取数据
     * @throws IOException 读异常
     * 这里使用读取数据长度控制读取，这可以保证数据精确读取，不存在读入缺失，或过量
     */
    public static byte[] in(InputStream in, int length) throws IOException {
        byte[] data = new byte[length];

        int offset = 0;
        int readLength = 1;
        while(length > 0 && readLength > 0) {
            readLength = in.read(data, offset, length);
            offset += readLength;
            length -= readLength;
        }

        return data;
    }
}
