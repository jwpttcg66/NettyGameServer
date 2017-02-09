package com.wolf.shoot.net.session.builder;

import com.wolf.shoot.net.session.ISession;
import io.netty.channel.Channel;

/**
 * Created by jwp on 2017/2/9.
 */
public interface ISessionBuilder {
    public ISession buildSession(Channel channel);
}
