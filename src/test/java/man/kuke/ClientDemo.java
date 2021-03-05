package man.kuke;

import man.kuke.core.NetNode;
import man.kuke.receive.Client;


/**
 * @author: kuke
 * @date: 2021/1/30 - 22:29
 * @description:
 */
public class ClientDemo {
    public static void main(String[] args) throws Exception {
        NetNode serverNode = new NetNode("127.0.0.1", 50000);
        NetNode centerRegistry = new NetNode("127.0.0.1", 54111);
        NetNode centerRequest = new NetNode("127.0.0.1", 54100);
        Client client = new Client(serverNode,centerRegistry, centerRequest,
                50001,50002);
        client.getResource("man", "E:\\test");
    }
}
