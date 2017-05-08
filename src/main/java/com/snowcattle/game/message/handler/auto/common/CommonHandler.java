package com.snowcattle.game.message.handler.auto.common;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.message.handler.AbstractMessageHandler;
import com.snowcattle.game.service.net.message.AbstractNetMessage;
import com.snowcattle.game.message.logic.tcp.online.client.OnlineHeartClientTcpMessage;
import com.snowcattle.game.service.net.message.command.MessageCommandIndex;


/**
 * Created by jiangwenping on 17/2/15.
 */
public abstract class CommonHandler extends AbstractMessageHandler {
    @MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_HEART_CLIENT_TCP_MESSAGE)
    public AbstractNetMessage handleOnlineHeartHeartMessage(OnlineHeartClientTcpMessage message) throws Exception{
        return handleOnlineHeartMessageImpl(message);
    }

    public abstract AbstractNetMessage handleOnlineHeartMessageImpl(OnlineHeartClientTcpMessage message) throws Exception;
}
