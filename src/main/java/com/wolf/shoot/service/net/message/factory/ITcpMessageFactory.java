package com.wolf.shoot.service.net.message.factory;

import com.wolf.shoot.service.net.message.AbstractNetMessage;

/**
 * Created by jwp on 2017/2/10.
 * 协议工厂
 */
public interface ITcpMessageFactory {
    public AbstractNetMessage createCommonErrorResponseMessage(int serial, int state);
    public AbstractNetMessage createCommonResponseMessage(int serial);
}
