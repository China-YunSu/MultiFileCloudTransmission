package man.kuke.receive;

import man.kuke.core.DataHeader;
import man.kuke.core.DataTransfer;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author: kuke
 * @date: 2021/1/29 - 17:17
 * @description:
 */
public class ResourceReceiver implements Runnable {
    private Socket socket;
    private DataTransfer dataTransfer;
    private InputStream in;
    private IAfterReceive afterReceive;

    public ResourceReceiver(Socket socket, Collection collection,IAfterReceive afterReceive) {
        try {
            this.socket = socket;
            this.afterReceive = afterReceive;
            in = socket.getInputStream();
            dataTransfer = new DataTransfer(new Handler(collection));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            while (dataTransfer.read(in) > 0) {
            }
        } catch (IOException e) {
            //TODO 断点续传
            close();
        }
        close();
        afterReceive.afterReceive();
    }

    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                socket = null;
            }
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                in = null;
            }
        }
    }

    private static class Handler implements DataProcesser {
        private Collection collection;

        public Handler(Collection collection) {
            this.collection = collection;
        }

        @Override
        public long dataProcesser(DataHeader dataHeader, byte[] data) throws IOException {
            return collection.receive(dataHeader,data);
        }
    }

}
