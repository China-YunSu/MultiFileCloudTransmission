package man.kuke.receive;

import man.kuke.core.DataHeader;
import man.kuke.core.FileSplit;
import man.kuke.core.Resource;
import man.kuke.core.ResourceFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * @author: kuke
 * @date: 2021/2/2 - 10:07
 * @description:
 */
public class ResourceRepository {
    private Collection collection;
    private ServerSocket serverSocket;
    private IAfterReceive afterReceive;

    public ResourceRepository(Resource resource, int receivePort,
                              IAfterReceive afterReceive) {
        try {
            this.afterReceive = afterReceive;
            this.collection = new Collection(resource);
            ResourceFactory.createFilePath(collection.getResource());
            serverSocket = new ServerSocket(receivePort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isAllReceived() {
        return collection.isAllRecovered();
    }

    public Resource getResource() {
        return collection.getResource();
    }


    public List<List<DataHeader>> getLostData(int truckCount) {
        new Thread(new ReceiveHandler(truckCount)).start();
        return FileSplit.blockSplit(collection.getLoseData(), truckCount);
    }

    public List<List<DataHeader>> getData(int truckCount) {
        new Thread(new ReceiveHandler(truckCount)).start();
        return FileSplit.fileSplit(collection.getResource().getFileInformations()
                ,truckCount);
    }


    private class ReceiveHandler extends AfterReceiveAdpter implements Runnable{
        private int truckCount;

        public ReceiveHandler(int truckCount) {
            this.truckCount = truckCount;
        }

        @Override
        public void run() {
            System.out.println("开始侦听");
            int count = truckCount;
            while (count > 0) {
                try {
                    Socket client = serverSocket.accept();
                    System.out.println("连接到传送者："
                            + client.getInetAddress().getHostAddress());
                    new Thread(new ResourceReceiver(client, collection,this)).start();
                    --count;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void afterReceive() {
            --truckCount;
            if (truckCount <= 0 ) {
                afterReceive.afterReceive(ResourceRepository.this);
            }
        }
    }
}
