package man.kuke;

import com.mec.util.FrameIsNullException;
import man.kuke.view.ReceiveProgressDialog;

import javax.swing.*;

/**
 * @author: kuke
 * @date: 2021/2/4 - 22:39
 * @description:
 */
public class AppTest6 {
    public static void main(String[] args) throws FrameIsNullException {
        JFrame jFrame = new JFrame();
        jFrame.setSize(500,500 );
        ReceiveProgressDialog receiveProgressDialog = new ReceiveProgressDialog(jFrame, 6) {

            @Override
            public void afterDialogShow() {

            }
        };
        receiveProgressDialog.showView();

    }
}
