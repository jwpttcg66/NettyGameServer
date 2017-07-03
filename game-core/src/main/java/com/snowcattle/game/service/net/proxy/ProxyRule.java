package com.snowcattle.game.service.net.proxy;

import com.snowcattle.game.service.lookup.ILongId;

/**
 * Created by jiangwenping on 2017/6/9.
 * 代理规则
 */
public class ProxyRule implements ILongId {

    private long serverId;

    /**
     * 远程地址
     */
    private String remoteHost;

    /**
     * 远程端口
     */
    private int remotePort;

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    @Override
    public long longId() {
        return serverId;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }
}
