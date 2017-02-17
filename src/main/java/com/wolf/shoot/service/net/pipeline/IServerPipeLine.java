package com.wolf.shoot.service.net.pipeline;

import com.wolf.shoot.net.message.NetMessage;
import io.netty.channel.Channel;

/**
 * Created by jiangwenping on 17/2/13.
 */
public interface IServerPipeLine {
    public void dispatchAction(Channel channel, NetMessage netMessage);
}
