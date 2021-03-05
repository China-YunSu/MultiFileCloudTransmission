package man.kuke;

import com.mec.util.IListener;
import man.kuke.core.NetNode;
import man.kuke.sender.ResourceHolder;
import man.kuke.sender.ServerConversation;
import man.kuke.spring.ioc.BeanFactory;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author: kuke
 * @date: 2021/1/30 - 22:21
 * @description:
 */
public class ServerDemo implements IListener {
    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException {
        BeanFactory.scannPackage("man.kuke.sender");

        ResourceHolder resourceHolder = (ResourceHolder) BeanFactory.getBean(ResourceHolder.class);
        resourceHolder.addLocalResource("man", "E:\\20_work");
        ServerConversation serverConversation =
                new ServerConversation(new NetNode("127.0.0.1", 54111),
                        new NetNode("127.0.0.1",50000));
        resourceHolder.setServerConversation(serverConversation);
        serverConversation.connectToRegistryCentor();
        serverConversation.register("man", "127.0.0.1", 50000);
        serverConversation.openServer();

//        ServerConversation serverConversation = (ServerConversation) BeanFactory.getBean(ServerConversation.class);
//        serverConversation.initialization();
//        serverConversation.addListener(new ServerDemo());
//
//        serverConversation.addLocalResource("kuke","D:\\Download");
//
//        serverConversation.openServer();
//
//
//        serverConversation.connectToRegistryCentor();
//        serverConversation.register("kuke", "127.0.0.1", 50000);

    }

    @Override
    public void processMessage(String s) {
        System.out.println(s);
    }
}
