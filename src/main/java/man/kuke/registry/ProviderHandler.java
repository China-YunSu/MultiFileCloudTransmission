package man.kuke.registry;

import com.mec.dispatcher.factory.anno.AActionClass;
import com.mec.dispatcher.factory.anno.AActionMethod;
import com.mec.dispatcher.factory.anno.AActionType;
import man.kuke.registry.mapper.ProviderService;
import man.kuke.spring.ioc.AutoWired;
import man.kuke.spring.ioc.Component;

/**
 * @author: kuke
 * @date: 2021/2/2 - 20:34
 * @description:
 */
@Component
@AActionClass
public class ProviderHandler implements ProviderService {
    @AutoWired
    private ServiceProvider serviceProvider;

    public void setServiceprovider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    @AActionMethod("register")
    public void register(@AActionType(parameterName = "service") String service,
                           @AActionType(parameterName = "ip") String ip,
                           @AActionType(parameterName = "port" ) int port) {
        serviceProvider.registerService(ip, port, service);
        System.out.println("register" + ip +" " +port + " "+service);
    }

    @Override
    @AActionMethod("logout")
    public void logout(@AActionType(parameterName = "service")String service,
                         @AActionType(parameterName = "ip")String ip,
                         @AActionType(parameterName = "port" )int port) {
        serviceProvider.logoutService(ip, port, service);
        System.out.println("logout" + ip +" " +port + " "+service);
    }

    @Override
    @AActionMethod("reportHealth")
    public void reportHealth(@AActionType(parameterName = "ip")String ip,
                              @AActionType(parameterName = "port")int port,
                              @AActionType(parameterName = "helth") int helth) {
        serviceProvider.reportHealth(ip, port, helth);
        System.out.println("reportHealth" + ip +" " +port + " "+helth);
    }

}
