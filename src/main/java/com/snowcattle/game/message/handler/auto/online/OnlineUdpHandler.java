package com.snowcattle.game.message.handler.auto.online;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.message.handler.AbstractMessageHandler;
import com.snowcattle.game.message.logic.udp.online.OnlineHeartClientUDPMessage;
import com.snowcattle.game.service.net.message.AbstractNetMessage;
import com.snowcattle.game.service.net.message.command.MessageCommandIndex;

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
