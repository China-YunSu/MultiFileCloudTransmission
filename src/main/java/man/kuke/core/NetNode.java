package man.kuke.core;

import java.util.Objects;

/**
 * @author: kuke
 * @date: 2021/1/30 - 21:19
 * @description:
 */
public class NetNode {
    private String ip;
    private int port;
    private int health;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public NetNode(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "NetNode{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", health=" + health +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NetNode netNode = (NetNode) o;
        return port == netNode.port &&
                health == netNode.health &&
                Objects.equals(ip, netNode.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port, health);
    }
}
