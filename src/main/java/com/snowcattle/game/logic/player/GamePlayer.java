package com.snowcattle.game.logic.player;

import com.snowcattle.game.service.lookup.ILongId;
import com.snowcattle.game.service.net.session.NettySession;
import com.snowcattle.game.service.net.session.NettyTcpNetMessageSender;

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

    private NettyTcpNetMessageSender nettyTcpNetMessageSender;

    public GamePlayer(NettyTcpNetMessageSender nettyTcpNetMessageSender, long playerId, int udpTocken) {
        this.nettyTcpNetMessageSender = nettyTcpNetMessageSender;
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

    @Override
    public long longId() {
        return playerId;
    }

    @Override
    public NettyTcpNetMessageSender getNettyTcpNetMessageSender() {
        return nettyTcpNetMessageSender;
    }

    public void setNettyTcpNetMessageSender(NettyTcpNetMessageSender nettyTcpNetMessageSender) {
        this.nettyTcpNetMessageSender = nettyTcpNetMessageSender;
    }
}
