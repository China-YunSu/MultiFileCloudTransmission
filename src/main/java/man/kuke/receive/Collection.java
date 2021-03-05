package man.kuke.receive;

import man.kuke.core.DataHeader;
import man.kuke.core.FileAccessor;
import man.kuke.core.FileInformation;
import man.kuke.core.Resource;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: kuke
 * @date: 2021/1/28 - 18:33
 * @description:
 */
public class Collection {
    private FileAccessor fileAccessor;
    private Map<Integer, Set<DataHeader>> tables;
    private Resource resource;

    public Collection(Resource resource) {
        this.resource = resource;
        fileAccessor = new FileAccessor(resource);
        this.tables = new ConcurrentHashMap<>();
        register(resource.getFileInformations());
    }

    public Resource getResource() {
        return resource;
    }


    public void register(FileInformation fileInformation) {
        long size = fileInformation.getSize();
        int fileId = fileInformation.getFileId();
        Set<DataHeader> blocks = ConcurrentHashMap.newKeySet();
        blocks.add(new DataHeader(fileId,0,size));
        tables.put(fileId, blocks);
    }

    public void register(List<FileInformation> fileInformations) {
        for (FileInformation fileInformation : fileInformations) {
            register(fileInformation);
        }
    }

    public long receive(DataHeader dataHeader, byte[] data) throws IOException {
        fileAccessor.writeFile(dataHeader, data);

        Set<DataHeader> blocks = tables.get(dataHeader.getFileId());

        updateBlocks(blocks,dataHeader);

        if (blocks.size() <= 0) {
            tables.remove(dataHeader.getFileId());
        }

        if (isRecovered(dataHeader.getFileId())) {
            fileAccessor.closeFile(dataHeader.getFileId());
        }

        return dataHeader.getLength();
    }

    private  void updateBlocks(Set<DataHeader> blocks, DataHeader dataHeader) {
        long fillLeft = dataHeader.getOffset();
        long fillRight = dataHeader.getOffset() + dataHeader.getLength();
        for (DataHeader block : blocks) {
            long left = block.getOffset();
            long right = block.getOffset() + block.getLength();
            if (left <= fillLeft && right >= fillRight) {
                long leftBlocklen = fillLeft - left;
                if (leftBlocklen > 0) {
                    blocks.add(new DataHeader(dataHeader.getFileId(), left,leftBlocklen));
                }
                long rightBlocklen = right - fillRight;
                if (rightBlocklen > 0) {
                    blocks.add(new DataHeader(dataHeader.getFileId(), fillRight,rightBlocklen));
                }
                blocks.remove(block);
                return;
            }
        }

    }

    private boolean isRecovered(Integer id) {
        return !tables.containsKey(id);
    }

    public boolean isAllRecovered() {
        return tables.isEmpty();
    }

    public java.util.Collection<Set<DataHeader>> getLoseData() {
        return tables.values();
    }



}
