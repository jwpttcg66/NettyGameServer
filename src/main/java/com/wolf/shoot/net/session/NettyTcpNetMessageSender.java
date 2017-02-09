package com.wolf.shoot.net.session;

import com.wolf.shoot.common.exception.NetMessageException;
import com.wolf.shoot.net.message.NetMessage;
import io.netty.channel.Channel;

/**
 * Created by jwp on 2017/2/9.
 */
public class NettyTcpNetMessageSender implements INetMessageSender{
    private final Channel channel;

    public NettyTcpNetMessageSender(Channel channel) {
        this.channel = channel;
    }

    @Override
    public boolean sendMessage(NetMessage message) throws NetMessageException {
        channel.write(message);
        return true;
    }

    @Override
    public void close() throws NetMessageException{

//        LOG.debug("Going to close tcp connection in class: {}", this
//                .getClass().getName());
//        Event event = Events.event(null, Events.DISCONNECT);
        if (channel.isActive())
        {
//            channel.write(event).addListener(ChannelFutureListener.CLOSE);
        }
        else
        {
            channel.close();
//            LOG.trace("Unable to write the Event {} with type {} to socket",
//                    event, event.getType());
        }
    }
}
