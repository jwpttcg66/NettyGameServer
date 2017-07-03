package com.snowcattle.game.logic.player;

import com.snowcattle.game.service.lookup.ILongId;
import com.snowcattle.game.service.net.tcp.session.NettyTcpNetMessageSender;

/**
 * Created by jiangwenping on 17/2/20.
 *
 */
public class GamePlayer implements  IPlayer, ILongId {

    //玩家id
    private long playerId;
    //玩家的udptoken
    private int udpToken;

    private NettyTcpNetMessageSender nettyTcpNetMessageSender;

    public GamePlayer(NettyTcpNetMessageSender nettyTcpNetMessageSender, long playerId, int udpToken) {
        this.nettyTcpNetMessageSender = nettyTcpNetMessageSender;
        this.playerId = playerId;
        this.udpToken = udpToken;
    }

    @Override
    public long getPlayerId() {
        return playerId;
    }

    @Override
    public int getPlayerUdpTocken() {
        return udpToken;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public int getUdpToken() {
        return udpToken;
    }

    public void setUdpToken(int udpToken) {
        this.udpToken = udpToken;
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
