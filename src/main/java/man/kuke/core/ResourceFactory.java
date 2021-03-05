package man.kuke.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author: kuke
 * @date: 2021/1/28 - 19:25
 * @description:
 */
public class ResourceFactory {
    private static Map<String,Resource> resourcePool;

    static  {
        resourcePool = new HashMap<>();
    }

    public Resource getResource(String resourceName) {
        return resourcePool.get(resourceName);
    }

    public static Resource createResource(String resourceName, String resourcePath) throws FileNotFoundException {
        Resource resource = new Resource(resourceName, resourcePath);
        browseLocalPackage(resource);
        resourcePool.put(resourceName, resource);

        return resource;
    }

    public static void browseLocalPackage(Resource resource) throws FileNotFoundException {
        String path = resource.getPath();
        File root = new File(path);
        if (!root.exists()) {
            throw new FileNotFoundException(path);
        }
        String absolutePath = root.getAbsolutePath();
        LinkedList<File> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            File file = queue.removeFirst();
            if (file.isDirectory()) {
                resource.addPath(file.getAbsolutePath()
                        .replace(absolutePath + File.separator, ""));
                File[] files = file.listFiles();
                if (files == null) {
                    continue;
                }
                for (File child : files) {
                    queue.addLast(child);
                }
            } else if (file.isFile() && file.length() > 0) {
                String relativePath = file.getAbsolutePath()
                        .replace(absolutePath + File.separator, "")
                        .replace(file.getName(), "");
                resource.addFile(new FileInformation(file.hashCode(), file.getName(),
                        relativePath,
                        file.length()));
            }
        }

    }

    public static void createFilePath(Resource resource) {
        String absolutePath = resource.getPath();
        if (!absolutePath.endsWith(File.separator)) {
            absolutePath = absolutePath+File.separator;
        }
        for (String relativepath : resource.getRelativepaths()) {
            File file = new File(absolutePath + relativepath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    public static void main(String[] args) throws  FileNotFoundException {
        Resource temp = ResourceFactory.createResource("temp", "D:\\Download");
        temp.setPath("D:\\temp");
        ResourceFactory.createFilePath(temp);
    }

}
