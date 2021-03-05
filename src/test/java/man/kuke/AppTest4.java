package man.kuke;

import com.mec.util.Bytes;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: kuke
 * @date: 2021/2/2 - 12:11
 * @description:
 */
public class AppTest4 {
    @Test
    public void fileWrite() throws IOException {
////        RandomAccessFile filereader = new RandomAccessFile("E:\\test\\aa.txt","r");
//        RandomAccessFile filwriter = new RandomAccessFile("E:\\test\\bb.txt","rw");
//        FileChannel channel = filwriter.getChannel();
//        byte[] data = new byte[0];
//        ByteBuffer wrap = ByteBuffer.wrap(data);
//        channel.write(wrap,0 );
//        filwriter.close();
          int a = -1;

        byte[] bytes = Bytes.toByte(a);
        int i = Bytes.toInt(bytes);
        System.out.println(i);
    }
}
