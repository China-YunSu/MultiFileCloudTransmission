package man.kuke.core;

import man.kuke.receive.DataProcesser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: kuke
 * @date: 2021/1/28 - 12:11
 * @description: 这里完成 如何传输数据。
 * 数据传入，读取多少，双方应该约定传输协议
 * 数据传入，读取数据头，在读入实体数据
 * 输出数据，输出数据头，在输出实体数据
 */
public class DataTransfer extends NetTransfer{
    private DataProcesser dataProcesser;

    public DataTransfer(DataProcesser dataProcesser) {
        this.dataProcesser = dataProcesser;
    }

    public void send(DataHeader dataHeader, OutputStream out, byte [] data) throws IOException {
        out(out,dataHeader.toBytes());
        out(out, data);
    }
    public void sendFin(OutputStream out) throws IOException {
        out(out,new DataHeader().setLength(-1).toBytes());
    }

    public long read(InputStream in) throws IOException {
        byte[] header = in(in, DataHeader.SIZE);
        DataHeader dataHeader = new DataHeader(header);
        if (dataHeader.getLength() < 0) {
            return -1;
        }
        byte[] data = in(in, (int) dataHeader.getLength());
        return dataProcesser.dataProcesser(dataHeader,data);
    }

}
