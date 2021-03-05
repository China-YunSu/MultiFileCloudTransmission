package man.kuke.core;

import com.mec.util.Bytes;

import java.util.Objects;

/**
 * @author: kuke
 * @date: 2021/1/28 - 12:06
 * @description:
 */
public class DataHeader {
    private int fileId;
    private long offset;
    private long length;
    public static final int SIZE = 20;


    public DataHeader() {
    }

    public DataHeader(int fileId, long offset, long length) {
        this.fileId = fileId;
        this.offset = offset;
        this.length = length;
    }

    public DataHeader(byte[] data) {
        fileId = Bytes.toInt(data, 0);
        offset = Bytes.toLong(data, 4);
        length = Bytes.toLong(data, 12);
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getLength() {
        return length;
    }

    public DataHeader setLength(long length) {
        this.length = length;
        return this;
    }

    public byte[] toBytes() {
        byte[] data = new byte[20];
        Bytes.toByte(data,fileId,0);
        Bytes.toByte(data,offset,4);
        Bytes.toByte(data,length,12);
        return data;
    }

    @Override
    public String toString() {
        return "DataHeader{" +
                "fileId=" + fileId +
                ", offset=" + offset +
                ", length=" + length +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass())  {
            return false;
        }
        DataHeader that = (DataHeader) o;
        return fileId == that.fileId &&
                offset == that.offset &&
                length == that.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileId, offset, length);
    }
}
