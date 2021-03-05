package man.kuke.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: kuke
 * @date: 2021/1/28 - 14:42
 * @description: 对文件定位读写，
 * read 的offset是相对读写指针位置
 * seek 可以调整读写指针
 * mode；指定文件访问方式 r、rw；如果写，接下来不能读
 */
public class FileAccessor {
    private final Map<Integer, RandomAccessFile> randomAccessFilePool;
    private Resource resource;

    public FileAccessor(Resource resource) {
        this.resource = resource;
        randomAccessFilePool = new ConcurrentHashMap<>();
    }

    public RandomAccessFile getRandomAccessFile(Integer key) {
        return randomAccessFilePool.get(key);
    }

    public FileChannel getFileChannel(int fileid, String model) throws FileNotFoundException {
        RandomAccessFile randomAccessFile = randomAccessFilePool.get(fileid);

        if (randomAccessFile == null) {
            synchronized (randomAccessFilePool) {
                randomAccessFile = randomAccessFilePool.get(fileid);
                if (randomAccessFile == null) {
                    FileInformation file = resource.getFile(fileid);
                    String path =  resource.getPath()
                            + file.getRelativePath() + file.getFileName();
                    randomAccessFile = new RandomAccessFile(path,model);
                    randomAccessFilePool.put(file.getFileId(),randomAccessFile);
                }
            }
        }

        return  randomAccessFile.getChannel();
    }

    public byte[] readFile(DataHeader dataHeader) throws IOException {
        FileChannel channel = getFileChannel(dataHeader.getFileId(), "r");
        ByteBuffer allocate = ByteBuffer.allocate((int) dataHeader.getLength());
        synchronized (channel) {
            channel.read(allocate, dataHeader.getOffset());
        }
        return allocate.array();
    }

    public void writeFile(DataHeader dataHeader,byte[] data) throws IOException {
        FileChannel channel = getFileChannel(dataHeader.getFileId(), "rw");
        synchronized (channel) {
            channel.write(ByteBuffer.wrap(data), dataHeader.getOffset());
        }
    }

    public void closeFile(Integer fileId) throws IOException {
        if (fileId == null) {
            return;
        }

        RandomAccessFile randomAccessFile = randomAccessFilePool.remove(fileId);
        if (randomAccessFile != null) {
            randomAccessFile.close();
        }
    }
}
