package man.kuke;

import man.kuke.core.DataHeader;
import man.kuke.core.DataTransfer;
import man.kuke.core.FileAccessor;
import man.kuke.core.FileInformation;
import man.kuke.core.Resource;
import man.kuke.core.ResourceFactory;
import man.kuke.receive.DataProcesser;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * @author: kuke
 * @date: 2021/1/31 - 16:17
 * @description:
 */
public class AppTest3 {
    public static void main(String[] args) throws Exception {
        Resource kuke = ResourceFactory.createResource("kuke", "E:\\20_work");
        Resource man = ResourceFactory.createResource("man", "E:\\test");

        List<FileInformation> fileInformations = kuke.getFileInformations();
        man.setRelativepaths(kuke.getRelativepaths());
        for (FileInformation fileInformation : fileInformations) {
            man.addFile(fileInformation);
        }
        ResourceFactory.createFilePath(man);
        FileAccessor fileAccessor1 = new FileAccessor(man);

        ServerSocket serverSocket = new ServerSocket(54199);

        Socket socket = serverSocket.accept();
        System.out.println("接收到客户端");
        DataTransfer dataTransfer = new DataTransfer(new DataProcesser() {
            @Override
            public long dataProcesser(DataHeader dataHeader, byte[] data) throws IOException {
                fileAccessor1.writeFile(dataHeader, data);
                return dataHeader.getLength();
            }
        });
        long read = 1;
        while (read > 0) {
            read = dataTransfer.read(socket.getInputStream());
        }



    }
}
