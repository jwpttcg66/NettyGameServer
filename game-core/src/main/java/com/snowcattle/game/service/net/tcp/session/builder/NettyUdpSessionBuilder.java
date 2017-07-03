package com.snowcattle.game.service.net.tcp.session.builder;

import com.snowcattle.game.service.net.tcp.session.ISession;
import com.snowcattle.game.service.net.udp.session.NettyUdpSession;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/2/17.
 * udp session的生成器
 */
@Service
public class NettyUdpSessionBuilder implements ISessionBuilder {
    @Override
    public ISession buildSession(Channel channel) {
        return new NettyUdpSession(channel);
    }
}
