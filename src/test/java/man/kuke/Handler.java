package man.kuke;

import man.kuke.core.DataHeader;
import man.kuke.core.FileAccessor;

import java.io.IOException;
import java.util.List;

/**
 * @author: kuke
 * @date: 2021/1/31 - 15:59
 * @description:
 */
public class Handler implements Runnable {
    private List<DataHeader> dataHeaders;
    private FileAccessor read;
    private  FileAccessor write;

    public Handler(List<DataHeader> dataHeaders, FileAccessor read, FileAccessor write) {
        this.dataHeaders = dataHeaders;
        this.read = read;
        this.write = write;
    }

    @Override
    public void run() {
        try {
            for (DataHeader dataHeader : dataHeaders) {
                byte[] bytes = read.readFile(dataHeader);
                write.writeFile(dataHeader, bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
