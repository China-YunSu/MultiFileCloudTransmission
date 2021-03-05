package man.kuke.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author: kuke
 * @date: 2021/1/28 - 17:13
 * @description:
 */
public class FileSplit {
    public static final int BUFFER_SIZE = 1 << 15;

    public static List<List<DataHeader>> fileSplit(List<FileInformation> fileInformation, int count) {
        List<List<DataHeader>> packages = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            packages.add(new LinkedList<DataHeader>());
        }

        int index = 0;
        for (FileInformation information : fileInformation) {
            long size = information.getSize();
            if (size < BUFFER_SIZE) {
                packages.get(index).add(new DataHeader(information.getFileId(), 0, size));
                index = (index + 1) % count;
            } else {
                long offset = 0;
                long restLen = information.getSize();
                int fileId = information.getFileId();
                while (restLen > 0) {
                    packages.get(index).add(new DataHeader(fileId,
                            offset,
                            restLen > BUFFER_SIZE ? BUFFER_SIZE : restLen));
                    offset += BUFFER_SIZE;
                    restLen -= BUFFER_SIZE;
                    index = (index + 1) % count;
                }
            }
        }
        return packages;
    }
    public static List<List<DataHeader>> blockSplit(Collection<Set<DataHeader>> fileData, int count) {
        List<List<DataHeader>> packages = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            packages.add(new LinkedList<>());
        }

        int index = 0;
        for (Collection<DataHeader> file : fileData) {
            for (DataHeader dataHeader : file) {
                long size = dataHeader.getLength();
                if (size < BUFFER_SIZE) {
                    packages.get(index).add(dataHeader);
                    index = (index + 1) % count;
                } else {
                    long offset = dataHeader.getOffset();
                    long restLen = dataHeader.getLength();
                    int fileId = dataHeader.getFileId();
                    while (restLen > 0) {
                        packages.get(index).add(new DataHeader(fileId,
                                offset,
                                restLen > BUFFER_SIZE ? BUFFER_SIZE : restLen));
                        offset += BUFFER_SIZE;
                        restLen -= BUFFER_SIZE;
                        index = (index + 1) % count;
                    }
                }
            }
        }

        return packages;
    }

}
