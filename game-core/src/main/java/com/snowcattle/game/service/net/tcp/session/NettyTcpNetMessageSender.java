package com.snowcattle.game.service.net.tcp.session;

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.exception.NetMessageException;
import com.snowcattle.game.service.message.AbstractNetMessage;
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
            //增加session 消息输出的错误日志
            if(Loggers.sessionLogger.isErrorEnabled()){
                Loggers.sessionLogger.error(e.toString(), e);
            }
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
            if( Loggers.sessionLogger.isDebugEnabled()) {
                Loggers.sessionLogger.debug("Unable to write the Event {} with type {} to socket");
            }
        }
    }
}
