package com.snowcattle.game.service.net.pipeline;

import com.snowcattle.game.service.net.message.AbstractNetMessage;
import io.netty.channel.Channel;

/**
 * Created by jiangwenping on 17/2/13.
 */
public interface IServerPipeLine {
    public void dispatchAction(Channel channel, AbstractNetMessage abstractNetMessage);
}
