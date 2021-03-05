package man.kuke;

import man.kuke.core.NetNode;
import man.kuke.receive.Client;

import java.util.Set;

/**
 * @author: kuke
 * @date: 2021/2/2 - 17:41
 * @description:
 */
public class ClientRquestCentorDemo {

    public static void main(String[] args) {
        NetNode serverNode = new NetNode("127.0.0.1", 54188);
        NetNode centerNode = new NetNode("127.0.0.1", 54100);
//        Client client = new Client(serverNode,centerNode,1);
//        Set<NetNode> addresses = client.getAddress("kuke");
//        for (NetNode address : addresses) {
//            System.out.println(address);
//        }
    }
}
