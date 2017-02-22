package com.wolf.shoot.service.net.message.factory;

import com.wolf.shoot.service.net.message.AbstractNetProtoBufUdpMessage;

/**
 * Created by jiangwenping on 17/2/22.
 */
public interface IUdpMessageFactory {
    public AbstractNetProtoBufUdpMessage createCommonErrorResponseMessage(int serial, int state);
    public AbstractNetProtoBufUdpMessage createCommonResponseMessage(int serial);
}
