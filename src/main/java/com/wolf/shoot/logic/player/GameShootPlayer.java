package com.wolf.shoot.logic.player;

import com.wolf.shoot.service.net.session.NettySession;

/**
 * Created by jiangwenping on 17/2/20.
 *
 */
public class GameShootPlayer implements   IShootPlayer{

    private NettySession nettyTcpSession;
    private NettySession nettyUdpSession;
    //玩家id
    private long playerId;
    //玩家的udptocken
    private int udpTocken;

    @Override
    public long getPlayerId() {
        return playerId;
    }

    @Override
    public int getPlayerUdpTocken() {
        return udpTocken;
    }

    @Override
    public NettySession getNettyTcpSession() {
        return nettyTcpSession;
    }

    @Override
    public NettySession getNettyUdpSesssion() {
        return nettyUdpSession;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getUdpTocken() {
        return udpTocken;
    }

    public void setUdpTocken(int udpTocken) {
        this.udpTocken = udpTocken;
    }

    public void setNettyTcpSession(NettySession nettyTcpSession) {
        this.nettyTcpSession = nettyTcpSession;
    }

    public NettySession getNettyUdpSession() {
        return nettyUdpSession;
    }

    public void setNettyUdpSession(NettySession nettyUdpSession) {
        this.nettyUdpSession = nettyUdpSession;
    }
}
