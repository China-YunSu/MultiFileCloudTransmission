package man.kuke;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author: kuke
 * @date: 2021/2/1 - 23:42
 * @description:
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:\\temp\\BaiduDisk\\abc.txt");
        FileChannel channel = fileInputStream.getChannel();

    }
}
