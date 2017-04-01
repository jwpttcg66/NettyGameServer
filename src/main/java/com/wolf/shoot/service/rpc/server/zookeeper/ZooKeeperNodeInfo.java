package com.wolf.shoot.service.rpc.server.zookeeper;

/**
 * Created by sunmosh on 2017/4/1.
 * zookeeper节点信息
 */
public class ZooKeeperNodeInfo {
    /**
     * 根节点信息
     */
    private ZooKeeperNodeBoEnum zooKeeperNodeBoEnum;
    /**
     * 服务器id
     */
    private String serverId;
    private String host;
    private String port;

    public ZooKeeperNodeInfo(ZooKeeperNodeBoEnum zooKeeperNodeBoEnum, String serverId, String host, String port) {
        this.zooKeeperNodeBoEnum = zooKeeperNodeBoEnum;
        this.serverId = serverId;
        this.host = host;
        this.port = port;
    }

    public ZooKeeperNodeBoEnum getZooKeeperNodeBoEnum() {
        return zooKeeperNodeBoEnum;
    }

    public void setZooKeeperNodeBoEnum(ZooKeeperNodeBoEnum zooKeeperNodeBoEnum) {
        this.zooKeeperNodeBoEnum = zooKeeperNodeBoEnum;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
