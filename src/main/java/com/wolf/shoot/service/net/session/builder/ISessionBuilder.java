package com.wolf.shoot.service.net.session.builder;

import com.wolf.shoot.service.net.session.ISession;
import io.netty.channel.Channel;

/**
 * Created by jwp on 2017/2/9.
 */
public interface ISessionBuilder {
    public ISession buildSession(Channel channel);
}
