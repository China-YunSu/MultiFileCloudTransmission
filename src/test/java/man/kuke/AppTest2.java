package man.kuke;

import man.kuke.core.DataHeader;
import man.kuke.core.DataTransfer;
import man.kuke.core.FileAccessor;
import man.kuke.core.FileInformation;
import man.kuke.core.FileSplit;
import man.kuke.core.Resource;
import man.kuke.core.ResourceFactory;

import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

/**
 * @author: kuke
 * @date: 2021/1/31 - 16:11
 * @description:
 */
public class AppTest2 {
    public static void main(String[] args) throws Exception {
        Resource kuke = ResourceFactory.createResource("kuke", "E:\\20_work");

        List<FileInformation> fileInformations = kuke.getFileInformations();
        FileAccessor fileAccessor = new FileAccessor(kuke);
        Socket socket = new Socket("127.0.0.1",54199);
        DataTransfer dataTransfer = new DataTransfer(null);
        OutputStream outputStream = socket.getOutputStream();

//        List<List<DataHeader>> split = FileSplit.split(fileInformations, 1);
//        for (List<DataHeader> dataHeaders : split) {
//            for (DataHeader dataHeader : dataHeaders) {
//                byte[] bytes = fileAccessor.readFile(dataHeader);
//                dataTransfer.send(dataHeader,outputStream,bytes);
//            }
//        }
//        dataTransfer.sendFin(outputStream);
//
//        for (FileInformation fileInformation : fileInformations) {
//            fileAccessor.closeFile(fileInformation.getFileId());
//        }

    }
}
