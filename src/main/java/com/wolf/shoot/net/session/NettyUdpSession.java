package com.wolf.shoot.net.session;

import com.wolf.shoot.net.message.NetMessage;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;

/**
 * Created by jwp on 2017/2/16.
 * netty的udp session
 */
public class NettyUdpSession  extends NettySession{

    private NettyUdpNetMessageSender nettyUdpNetMessageSender;
    public NettyUdpSession(Channel channel) {
        super(channel);
        this.nettyUdpNetMessageSender = new NettyUdpNetMessageSender(this);
    }

    public void write(NetMessage msg) throws Exception {
        if (msg != null) {
            channel.writeAndFlush(msg).sync();
        }
    }
}
