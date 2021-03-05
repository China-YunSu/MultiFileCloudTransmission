package man.kuke.sender;

import man.kuke.core.DataHeader;
import man.kuke.core.DataTransfer;
import man.kuke.core.FileAccessor;
import man.kuke.core.Resource;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Comparator;
import java.util.List;

/**
 * @author: kuke
 * @date: 2021/1/29 - 11:06
 * @description:
 */
public class ResourceSender {
    private Resource resource;
    private String ip;
    private int port;
    private IAfterSendAction afterSendAction;

    public ResourceSender(Resource resource,IAfterSendAction afterSendAction) {
        this.resource = resource;
        this.afterSendAction = afterSendAction;
    }

    public void setAfterSendAction(IAfterSendAction afterSendAction) {
        this.afterSendAction = afterSendAction;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void send(List<DataHeader> dataHeaderList) throws IOException {
        new Thread(new Handler(dataHeaderList)).start();
    }

    private void close(Socket socket, OutputStream out) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class Handler implements Runnable {
        private List<DataHeader> dataHeaderList;
        private Socket socket;
        private OutputStream out;

        public Handler(List<DataHeader> dataHeaderList) {
            this.dataHeaderList = dataHeaderList;
        }

        @Override
        public void run() {
            try {
                socket = new Socket(ip, port);
                out = socket.getOutputStream();

                dataHeaderList.sort(new Comparator<DataHeader>() {
                    @Override
                    public int compare(DataHeader o1, DataHeader o2) {
                        return Integer.compare(o1.getFileId(), o2.getFileId());
                    }
                });
                FileAccessor fileAccessor = new FileAccessor(resource);
                DataTransfer dataTransfer = new DataTransfer(null);
                Integer id = null;
                for (DataHeader dataHeader : dataHeaderList) {
                    if (id == null || id != dataHeader.getFileId()) {
                        fileAccessor.closeFile(id);
                        id = dataHeader.getFileId();
                    }
                    byte[] bytes = fileAccessor.readFile(dataHeader);
                    dataTransfer.send(dataHeader,out,bytes);
                }

                dataTransfer.sendFin(out);
                fileAccessor.closeFile(id);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(socket,out);
            }
            afterSendAction.afterSend();
            System.out.println("传送完毕");
        }
    }
}
