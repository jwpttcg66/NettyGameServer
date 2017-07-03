package com.snowcattle.game.service.net.tcp.session.builder;

import com.snowcattle.game.service.net.tcp.session.ISession;
import io.netty.channel.Channel;

/**
 * Created by jwp on 2017/2/9.
 */
public interface ISessionBuilder {
    public ISession buildSession(Channel channel);
}
