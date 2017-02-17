package com.wolf.shoot.net.session.builder;

import com.wolf.shoot.net.session.ISession;
import com.wolf.shoot.net.session.NettyUdpSession;
import com.wolf.shoot.service.IServerService;
import io.netty.channel.Channel;

/**
 * Created by jwp on 2017/2/17.
 * udp session的生成器
 */
public class NettyUdpSessionBuilder implements ISessionBuilder {
    @Override
    public ISession buildSession(Channel channel) {
        return new NettyUdpSession(channel);
    }
}
