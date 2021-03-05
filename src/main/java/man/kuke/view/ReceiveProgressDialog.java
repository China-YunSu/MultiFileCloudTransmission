package man.kuke.view;

import com.mec.util.IMecView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: kuke
 * @date: 2021/2/4 - 21:42
 * @description:
 */
public abstract class ReceiveProgressDialog extends JDialog implements IMecView {
    private static final int WIDTH = 340;
    private static final int MIN_HEIGHT = 170;

    private static final String RECEIVE_FILE_TOTAL_COUNT = "本次共接收#个文件";
    private int receiveFileCount;
    private JPanel jpnlFiles;

    private Map<Integer,ReceiveFileProgress> receiveFilePool;

    public ReceiveProgressDialog(JFrame parent, int receiveFileCount) {
        super(parent,true);
        this.receiveFileCount = receiveFileCount;
        this.receiveFilePool = new HashMap<>();
        initView();
    }

    public void removeReceiveFile(int fileNo) {
        ReceiveFileProgress receiveFileProgress =
                this.receiveFilePool.get(fileNo);
        if (receiveFileProgress != null) {
            this.jpnlFiles.remove(receiveFileProgress);
            receiveFilePool.remove(fileNo);
            resizeDialog();
        }
    }

    public boolean receiveFileExist(int fileNo) {
        return this.receiveFilePool.containsKey(fileNo);
    }

    public void receiveFileSection(int fileNo, int receiveLen) {
        ReceiveFileProgress receiveFileProgress = this.receiveFilePool.get(fileNo);
        receiveFileProgress.receiveFileSection(receiveLen);
    }

    public void addReceiveFile(int fileNo, String fileName, long fileSize) {
        addReceiveFile(fileNo, fileName, 0L, fileSize);
    }

    public void addReceiveFile(int fileNo, String fileName, long receivedSize, long fileSize) {
        ReceiveFileProgress receiveFileProgress =
                new ReceiveFileProgress(jpnlFiles, fileName, receivedSize, fileSize);
        this.receiveFilePool.put(fileNo, receiveFileProgress);
        this.jpnlFiles.add(receiveFileProgress);
        resizeDialog();
    }

    private void resizeDialog() {
        int receiveFileProgressCount = this.receiveFilePool.size();
        int height = MIN_HEIGHT
                + receiveFileProgressCount * (ReceiveFileProgress.HEIGHT)
                + (receiveFileProgressCount > 1
                        ? (receiveFileProgressCount - 1) * IMecView.PADDING * 3
                        : 0);
        this.setSize(WIDTH,height);
        this.setLocationRelativeTo(this.getParent());
    }



    private String getFileCountString(int fileCount) {
        return RECEIVE_FILE_TOTAL_COUNT.replaceAll("#", String.valueOf(fileCount));
    }

    @Override
    public void dealEvent() {
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                afterDialogShow();
            }
        });
    }

    public abstract void afterDialogShow();

    @Override
    public void reinit() {
    }

    @Override
    public void init() {
        this.setSize(WIDTH, MIN_HEIGHT);
        this.setLocationRelativeTo(this.getParent());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        JLabel jlblEast = new JLabel(" ");
        jlblEast.setFont(smallFont);
        this.add(jlblEast, BorderLayout.EAST);

        JLabel jlblWest = new JLabel(" ");
        jlblWest.setFont(smallFont);
        this.add(jlblWest, BorderLayout.WEST);


        // 标题和文件接收数量
        JPanel jpnlHead = new JPanel(new BorderLayout());
        this.add(jpnlHead, BorderLayout.NORTH);

        JLabel jlblHeadEast = new JLabel(" ");
        jlblHeadEast.setFont(smallFont);
        jpnlHead.add(jlblHeadEast, BorderLayout.EAST);

        JLabel jlblHeadWest = new JLabel(" ");
        jlblHeadWest.setFont(smallFont);
        jpnlHead.add(jlblHeadWest, BorderLayout.WEST);

        JLabel jlblTopic = new JLabel("文件接收进度", JLabel.CENTER);
        jlblTopic.setFont(topicFont);
        jlblTopic.setForeground(topicColor);
        jpnlHead.add(jlblTopic, BorderLayout.NORTH);

        JPanel jpnlFileCount = new JPanel(new GridLayout(0, 1));
        jpnlHead.add(jpnlFileCount, BorderLayout.CENTER);

        JLabel jlblReceiveFileCount = new JLabel(
                getFileCountString(this.receiveFileCount), JLabel.CENTER);
        jlblReceiveFileCount.setFont(normalFont);
        jpnlFileCount.add(jlblReceiveFileCount);

        JProgressBar jpgbReceiveFileCount = new JProgressBar(0, receiveFileCount);
        jpgbReceiveFileCount.setFont(normalFont);
        jpgbReceiveFileCount.setStringPainted(true);
        jpgbReceiveFileCount.setString("0 / " + this.receiveFileCount);
        jpnlFileCount.add(jpgbReceiveFileCount);

        // 文件接收进度
        jpnlFiles = new JPanel();
        GridLayout gdltFiles = new GridLayout(0, 1);
        gdltFiles.setVgap(PADDING);
        jpnlFiles.setLayout(gdltFiles);
        this.add(jpnlFiles, BorderLayout.CENTER);
    }

    @Override
    public RootPaneContainer getFrame() {
        return this;
    }
}
