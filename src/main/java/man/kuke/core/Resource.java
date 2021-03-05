package man.kuke.core;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: kuke
 * @date: 2021/1/28 - 20:16
 * @description:
 */
public class Resource {
    private String resourceName;
    private String path;
    private int resourceId;
    private Map<Integer,FileInformation> fileInformations;
    private List<String> relativepaths;

    public void setRelativepaths(List<String> relativepaths) {
        this.relativepaths = relativepaths;
    }

    public Resource(String resourceName, String path) {
        this.resourceName = resourceName;
        setPath(path);
        relativepaths = new LinkedList<>();
        fileInformations = new HashMap<>();
        resourceId = (resourceName + path).hashCode();
    }

    public void addPath(String path) {
        relativepaths.add(path);
    }

    public List<String> getRelativepaths() {
        return relativepaths;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        if (!path.endsWith(File.separator)) {
            path += File.separator;
        }
        this.path = path;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void addFile(FileInformation fileInformation) {
        fileInformations.put(fileInformation.getFileId(), fileInformation);
    }

    public int fileCount() {
        return fileInformations.size();
    }

    public List<FileInformation> getFileInformations() {
        return new LinkedList<>(fileInformations.values());
    }

    public FileInformation getFile(Integer fileId) {
        return fileInformations.get(fileId);
    }

    @Override
    public String toString() {
        return "Resource{" +
                "resourceName='" + resourceName + '\'' +
                ", path='" + path + '\'' +
                ", resourceId=" + resourceId +
                ", fileInformations=" + fileInformations +
                ", relativepaths=" + relativepaths +
                '}';
    }
}
