package com.wolf.shoot.net.session;

import com.wolf.shoot.common.constant.Loggers;
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
        channel.writeAndFlush(message);
        return true;
    }

    @Override
    public void close() throws NetMessageException{

        Loggers.sessionLogger.debug("Going to close tcp connection in class: {}", this
                .getClass().getName());
//        Event event = Events.event(null, Events.DISCONNECT);
        if (channel.isActive())
        {
            channel.close();
//            channel.write(event).addListener(ChannelFutureListener.CLOSE);
        }
        else
        {
            channel.close();
//            Loggers.sessionLogger.debug("Unable to write the Event {} with type {} to socket",
//                    event, event.getType());

            Loggers.sessionLogger.debug("Unable to write the Event {} with type {} to socket");
        }
    }
}
