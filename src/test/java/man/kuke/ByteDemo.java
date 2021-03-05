package man.kuke;


import com.mec.util.Bytes;
import man.kuke.core.DataHeader;

/**
 * @author: kuke
 * @date: 2021/1/31 - 16:46
 * @description:
 */
public class ByteDemo {
    public static void main(String[] args) {
        DataHeader dataHeader = new DataHeader(1, 2L, 3L);
        byte[] bytes = dataHeader.toBytes();
        String s = Bytes.toString(bytes);
        System.out.println(s);
        DataHeader dataHeader1 = new DataHeader(bytes);
        System.out.println(dataHeader1);
    }
}
