package com.wolf.shoot.message.handler.auto.online;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.message.handler.AbstractMessageHandler;
import com.wolf.shoot.message.logic.udp.online.OnlineHeartClientUDPMessage;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.command.MessageCommandIndex;

/**
 * Created by jiangwenping on 17/2/22.
 */
public abstract class OnlineUdpHandler extends AbstractMessageHandler {

    @MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_HEART_CLIENT_UDP_MESSAGE)
    public AbstractNetMessage handleOnlineHeartClientUdpMessage(OnlineHeartClientUDPMessage message) throws Exception{
        return handleOnlineHeartClientUdpMessageImpl(message);
    }

    public abstract AbstractNetMessage handleOnlineHeartClientUdpMessageImpl(OnlineHeartClientUDPMessage message) throws Exception;
}
