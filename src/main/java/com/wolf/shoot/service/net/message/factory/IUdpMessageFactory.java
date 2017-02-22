package com.wolf.shoot.service.net.message.factory;

import com.wolf.shoot.service.net.message.AbstractNetMessage;

/**
 * Created by jiangwenping on 17/2/22.
 */
public interface IUdpMessageFactory {
    public AbstractNetMessage createCommonErrorResponseMessage(int serial, int state);
    public AbstractNetMessage createCommonResponseMessage(int serial);
}
