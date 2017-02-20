package com.wolf.shoot.service.net.session;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.exception.NetMessageException;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import io.netty.channel.Channel;

/**
 * Created by jwp on 2017/2/9.
 */
public class NettyTcpNetMessageSender implements INetMessageSender{

    private final NettySession nettySession;
    public NettyTcpNetMessageSender(NettySession nettySession) {
        this.nettySession = nettySession;
    }

    @Override
    public boolean sendMessage(AbstractNetMessage message) throws NetMessageException {
        try {
            nettySession.write(message);
        }catch (Exception e){
            throw new NetMessageException("write tcp netmessage exception", e);
        }

        return true;
    }

    @Override
    public void close() throws NetMessageException{

        Loggers.sessionLogger.debug("Going to close tcp connection in class: {}", this
                .getClass().getName());
//        Event event = Events.event(null, Events.DISCONNECT);
        Channel channel = nettySession.channel;
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
