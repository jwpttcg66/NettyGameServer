package com.snowcattle.game.service.net.udp.session;

import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.net.tcp.session.NettySession;
import io.netty.channel.Channel;

/**
 * Created by jwp on 2017/2/16.
 * netty的udp session
 */
public class NettyUdpSession  extends NettySession {

    private NettyUdpNetMessageSender nettyUdpNetMessageSender;
    public NettyUdpSession(Channel channel) {
        super(channel);
        this.nettyUdpNetMessageSender = new NettyUdpNetMessageSender(this);
    }

    public void write(AbstractNetMessage msg) throws Exception {
        if (msg != null) {
            channel.writeAndFlush(msg).sync();
        }
    }
}
