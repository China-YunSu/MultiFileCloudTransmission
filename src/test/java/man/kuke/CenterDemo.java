package man.kuke;

import com.mec.dispatcher.factory.anno.AnnoActionBeanFactory;
import com.mec.util.IListener;
import man.kuke.registry.RegistryCenter;
import man.kuke.rmi.core.MethodFactory;
import man.kuke.spring.ioc.BeanFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * @author: kuke
 * @date: 2021/2/2 - 17:41
 * @description:
 */
public class CenterDemo implements IListener {
    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException {
        RegistryCenter registryCenter = new RegistryCenter(54111,54100);
        registryCenter.addListener(new CenterDemo());

//        Scanner in = new Scanner(System.in);
//        String cmd = in.nextLine();

//        while (!cmd.equalsIgnoreCase("ex")) {
//            if (cmd.equalsIgnoreCase("st")) {
                registryCenter.openServer();
//            }
//            cmd = in.nextLine();
//        }

//        registryCenter.closeServer();
//        in.close();
    }

    @Override
    public void processMessage(String s) {
        System.out.println(s);
    }
}
