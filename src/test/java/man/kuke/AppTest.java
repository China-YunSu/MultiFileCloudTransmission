package man.kuke;

import static org.junit.Assert.assertTrue;

import man.kuke.core.FileAccessor;
import man.kuke.core.FileInformation;
import man.kuke.core.Resource;
import man.kuke.core.ResourceFactory;

import java.io.IOException;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest implements Runnable
{
    /**
     * Rigorous Test :-)
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Resource kuke = ResourceFactory.createResource("kuke", "E:\\20_work\\excel");
        Resource man = ResourceFactory.createResource("man", "E:\\test");

        List<FileInformation> fileInformations = kuke.getFileInformations();
        man.setRelativepaths(kuke.getRelativepaths());
        FileAccessor fileAccessor = new FileAccessor(kuke);
        for (FileInformation fileInformation : fileInformations) {
            man.addFile(fileInformation);
        }
        FileAccessor fileAccessor1 = new FileAccessor(man);


        for (FileInformation fileInformation : fileInformations) {
//            List<List<DataHeader>> split = FileSplit.split(fileInformation, 1);
//            for (List<DataHeader> dataHeaders : split) {
//                new Thread( new Handler(dataHeaders, fileAccessor,fileAccessor1)).start();
//            }
        }
        Thread.sleep(5000);
        for (FileInformation fileInformation : fileInformations) {
            fileAccessor1.closeFile(fileInformation.getFileId());
            fileAccessor.closeFile(fileInformation.getFileId());
        }
    }

    @Override
    public void run() {

    }
}
