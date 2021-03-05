package man.kuke.sender;

import com.mec.util.ArgumentMaker;
import com.mec.util.IListener;
import man.kuke.core.NetNode;
import man.kuke.nio.core.Client;
import man.kuke.nio.core.ClientActionNotSetException;
import man.kuke.nio.core.ClientActionProcessAdpter;
import man.kuke.registry.mapper.ProviderService;
import man.kuke.rmi.core.MethodFactory;
import man.kuke.rmi.core.RMIServer;

/**
 * @author: kuke
 * @date: 2021/1/30 - 20:59
 * @description:
 */
public class ServerConversation implements ProviderService {
    private RMIServer serviceServer;
    private Client regisCenter;
    private NetNode serverNode;
    private int health;
    public ServerConversation(NetNode registryCenter,NetNode serverNode) {
        this.serviceServer = new RMIServer();
        this.health = 10;
        this.serverNode = serverNode;
        MethodFactory.loadMapper("/man/kuke/sender/mapper/handlerMapping.xml");
        regisCenter = new Client();
        setServicePort(serverNode.getPort());
        setRegistryCenterPort(registryCenter.getPort());
        setRegistryCenterIp(registryCenter.getIp());
        regisCenter.setClientProcess(new ClientActionProcessAdpter());
    }


    public void connectToRegistryCentor()  {
        try {
            regisCenter.connectToServer();
        } catch (ClientActionNotSetException e) {
            e.printStackTrace();
        }
    }

    public boolean isOpen() {
        return serviceServer.isOpen();
    }


    public void setRegistryCenterPort(int port) {
        regisCenter.setPort(port);
    }

    public void setRegistryCenterIp(String ip) {
        regisCenter.setIp(ip);
    }

    public void setServicePort(int port) {
        serviceServer.setPort(port);
    }

    public void addListener(IListener listener) {
        serviceServer.addListener(listener);
    }

    public void removeListener(IListener listener) {
        serviceServer.removeListener(listener);
    }


    public void openServer() {
        serviceServer.openServer();
    }

    public void closeServer() {
        regisCenter.offLine();
        serviceServer.close();
    }

    public void register(String service) {
        register(service,serverNode.getIp(),serverNode.getPort());
    }

    public void logout(String service) {
        logout(service,serverNode.getIp(),serverNode.getPort());
    }

    public void reportHealth(int value) {
        reportHealth(serverNode.getIp(),serverNode.getPort(),value + health);
    }

    @Override
    public void register(String service, String ip, int port) {
        regisCenter.request("register","",
                new ArgumentMaker()
                        .addArgument("service",service)
                        .addArgument("ip",ip)
                        .addArgument("port",port)
                        .toString());
    }

    @Override
    public void logout(String service, String ip, int port) {
        regisCenter.request("logout","",
                new ArgumentMaker()
                        .addArgument("service",service)
                        .addArgument("ip",ip)
                        .addArgument("port",port)
                        .toString());
    }

    @Override
    public void reportHealth(String ip, int port, int helth) {
        regisCenter.request("reportHealth","",
                new ArgumentMaker()
                        .addArgument("ip",ip)
                        .addArgument("port",port)
                        .addArgument("helth",helth)
                        .toString());
    }
}
