package man.kuke;

import man.kuke.sender.ServerConversation;
import man.kuke.spring.ioc.BeanFactory;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author: kuke
 * @date: 2021/2/2 - 21:27
 * @description:
 */
public class ServerRequestCentorDemo {
    public static void main(String[] args) throws URISyntaxException, IOException, ClassNotFoundException {
        BeanFactory.scannPackage("man.kuke.sender");

        ServerConversation serverConversation = (ServerConversation) BeanFactory.getBean(ServerConversation.class);
        serverConversation.connectToRegistryCentor();
        serverConversation.register("kuke", "127.0.0.1", 54188);
//        serverConversation.reportHealth("127.0.0.1", 54188,10);
//        serverConversation.logout("kuke", "127.0.0.1", 45464);
    }
}
