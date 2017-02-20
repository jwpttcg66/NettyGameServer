package com.wolf.shoot.service.net.pipeline;

import com.wolf.shoot.service.net.message.AbstractNetMessage;
import io.netty.channel.Channel;

/**
 * Created by jiangwenping on 17/2/13.
 */
public interface IServerPipeLine {
    public void dispatchAction(Channel channel, AbstractNetMessage abstractNetMessage);
}
