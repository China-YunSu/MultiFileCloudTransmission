package man.kuke.core;

/**
 * @author: kuke
 * @date: 2021/1/28 - 17:15
 * @description:
 */
public class  FileInformation {
    private int fileId;
    private String fileName;
    private String relativePath;
    private long size;

    public FileInformation(int fileId, String fileName, String relativePath, long size) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.relativePath = relativePath;
        this.size = size;
    }


    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    @Override
    public String toString() {
        return "FileInformation{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", size=" + size +
                '}';
    }
}
