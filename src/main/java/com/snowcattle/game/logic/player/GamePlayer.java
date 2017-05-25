package com.snowcattle.game.logic.player;

import com.snowcattle.game.service.lookup.ILongId;
import com.snowcattle.game.service.net.session.NettySession;

/**
 * Created by jiangwenping on 17/2/20.
 *
 */
public class GamePlayer implements  IPlayer, ILongId {

    private NettySession nettyTcpSession;
    //玩家id
    private long playerId;
    //玩家的udptocken
    private int udpTocken;

    public GamePlayer(NettySession nettyTcpSession, long playerId, int udpTocken) {
        this.nettyTcpSession = nettyTcpSession;
        this.playerId = playerId;
        this.udpTocken = udpTocken;
    }

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

    @Override
    public long longId() {
        return playerId;
    }
}
