package man.kuke.view;

import com.mec.util.IMecView;

import javax.swing.*;
import java.awt.*;

/**
 * @author: kuke
 * @date: 2021/2/4 - 21:28
 * @description:
 */
public class ReceiveFileProgress extends JPanel {
    public static final int HEIGHT = 30;

    private long receivedLen;

    private JProgressBar jpgbReceiveFile;

    public ReceiveFileProgress(JPanel jPanelParent,String fileName,long filesize) {
        this(jPanelParent,fileName,0L,filesize);
    }

    public ReceiveFileProgress(JPanel jPanelParent,String fileName,long receivedLen,long filesize) {
        this.receivedLen = receivedLen;
        this.setLayout(new GridLayout(0, 1));
        int parentWidth = jPanelParent.getWidth();
        this.setSize(parentWidth,HEIGHT);

        JLabel jlblFileName = new JLabel(fileName,JLabel.CENTER);
        jlblFileName.setFont(IMecView.normalFont);
        this.add(jlblFileName);

        this.jpgbReceiveFile = new JProgressBar();
        this.jpgbReceiveFile.setFont(IMecView.normalFont);
        this.jpgbReceiveFile.setStringPainted(true);
        this.jpgbReceiveFile.setMaximum((int) filesize);
        this.jpgbReceiveFile.setValue((int) this.receivedLen);
        this.add(this.jpgbReceiveFile);
    }

    public void receiveFileSection(int receivedLen) {
        this.receivedLen += receivedLen;
        this.jpgbReceiveFile.setValue((int) this.receivedLen);
    }
}
