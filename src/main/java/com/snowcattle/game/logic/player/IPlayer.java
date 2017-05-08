package com.snowcattle.game.logic.player;

import com.snowcattle.game.service.net.session.NettySession;

/**
 * Created by jiangwenping on 17/2/20.
 */
public interface IPlayer {
    public long getPlayerId();
    public int getPlayerUdpTocken();
    public NettySession getNettyTcpSession();
}
