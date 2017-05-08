package com.snowcattle.game.service.rpc.server;

/**
 * Created by jiangwenping on 17/4/1.
 */
public class RpcNodeInfo {

    /**
     * 服务器id
     */
    private String serverId;
    private String host;
    private String port;

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

    public int getIntPort(){
        return Integer.parseInt(port);
    }
}
