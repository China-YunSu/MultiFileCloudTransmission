package man.kuke.registry.mapper;

/**
 * @author: kuke
 * @date: 2021/2/2 - 20:35
 * @description:
 */
public interface ProviderService {
    void register(String service,String ip,int port);
    void logout(String service,String ip,int port);
    void reportHealth(String ip,int port,int helth);
}
