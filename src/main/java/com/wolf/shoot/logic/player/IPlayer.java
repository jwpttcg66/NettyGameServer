package com.wolf.shoot.logic.player;

import com.wolf.shoot.service.net.session.NettySession;

/**
 * Created by jiangwenping on 17/2/20.
 */
public interface IPlayer {
    public long playerId();
    public NettySession getNettTcpSession();
}
