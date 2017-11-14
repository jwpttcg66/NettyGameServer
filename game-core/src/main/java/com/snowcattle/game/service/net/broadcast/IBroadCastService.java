package com.snowcattle.game.service.net.broadcast;

import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.INetMessage;

/**
 * Created by jiangwenping on 2017/11/14.
 */
public interface IBroadCastService {
    public void broadcastMessage(long sessionId, AbstractNetMessage NetMessage);
}
