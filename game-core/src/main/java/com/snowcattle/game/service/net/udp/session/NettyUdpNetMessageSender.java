package com.snowcattle.game.service.net.udp.session;

import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.common.exception.NetMessageException;
import com.snowcattle.game.service.net.tcp.session.INetMessageSender;
import com.snowcattle.game.service.net.tcp.session.NettySession;

/**
 * Created by jiangwenping on 17/2/15.
 * udp的消息发送器
 */
public class NettyUdpNetMessageSender implements INetMessageSender {
    private final NettySession nettySession;

    public NettyUdpNetMessageSender(NettySession nettySession) {
        this.nettySession = nettySession;
    }

    @Override
    public boolean sendMessage(AbstractNetMessage message) throws NetMessageException {
        try {
            nettySession.write(message);
        }catch (Exception e){
            throw new NetMessageException("write udp netmessage error", e);
        }
        return true;
    }

    @Override
    public void close() throws NetMessageException {

    }
}
