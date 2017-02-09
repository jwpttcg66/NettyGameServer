package com.wolf.shoot.net.session;

import io.netty.channel.Channel;

/**
 * Created by jwp on 2017/2/9.
 * netty tcp的session
 */
public class NettyTcpSession extends NettySession{

    private NettyTcpNetMessageSender nettyTcpNetMessageSender;

    public NettyTcpSession(Channel channel) {
        super(channel);
        nettyTcpNetMessageSender = new NettyTcpNetMessageSender(channel);
    }
}
