package man.kuke.registry;

import com.mec.dispatcher.factory.anno.AnnoActionBeanFactory;
import com.mec.util.IListener;
import man.kuke.nio.core.ActionProcesser;
import man.kuke.nio.core.IServerAction;
import man.kuke.nio.core.NIOServer;
import man.kuke.rmi.core.MethodFactory;
import man.kuke.rmi.core.RMIServer;
import man.kuke.spring.ioc.BeanFactory;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author: kuke
 * @date: 2021/2/2 - 16:27
 * @description:
 */
public class RegistryCenter {
    private RMIServer rmiServer;
    private NIOServer nioServer;

    public RegistryCenter(int serviceRegistryPort,int serviceRequsetPort) {
        rmiServer = new RMIServer();
        nioServer = new NIOServer();
        initialization(serviceRegistryPort,serviceRequsetPort);
    }

    private void initialization(int serviceRegistryPort,int serviceRequsetPort) {
        try {
            //生成处理器
            BeanFactory.scannPackage("man.kuke.registry");

            //设置对端掉线处理器
            nioServer.setServerAction(
                    (IServerAction) BeanFactory.getBean(PeerDownHandler.class));

            setRMIPort(serviceRequsetPort);
            setNioPort(serviceRegistryPort);
            //建立RMI处理器映射
            MethodFactory.loadMapper("/man/kuke/registry/mapper/ConsumeActionMapper.xml");

            //建立NIO处理器映射关系
            AnnoActionBeanFactory factory = new AnnoActionBeanFactory();
            factory.scannActionMapping("man.kuke.registry");
            nioServer.setActionProcess(new ActionProcesser(factory));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void setNioPort(int port) {
        nioServer.setPort(port);
    }

    public void setRMIPort(int port) {
        rmiServer.setPort(port);
    }

    public void openServer() {
        rmiServer.openServer();
        nioServer.openServer();
    }

    public void closeServer() {
        rmiServer.close();
        nioServer.shutDown();
    }

    public void addListener(IListener listener) {
        nioServer.addListener(listener);
        rmiServer.addListener(listener);
    }

    public void removeListener(IListener listener) {
        nioServer.removeListener(listener);
        rmiServer.removeListener(listener);
    }
}


