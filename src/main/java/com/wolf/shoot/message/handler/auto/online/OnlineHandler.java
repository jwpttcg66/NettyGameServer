package com.wolf.shoot.message.handler.auto.online;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.message.handler.AbstractMessageHandler;
import com.wolf.shoot.message.logic.tcp.online.client.OnlineLoginClientTcpMessage;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.command.MessageCommandIndex;

/**
 * Created by jiangwenping on 17/2/21.
 */

public abstract class OnlineHandler extends AbstractMessageHandler {
    @MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_LOGIN_TCP_CLIENT_MESSAGE)
    public AbstractNetMessage handleOnlineLoginClientTcpMessage(OnlineLoginClientTcpMessage message) throws Exception{
        return handleOnlineLoginClientTcpMessageImpl(message);
    }

    public abstract AbstractNetMessage handleOnlineLoginClientTcpMessageImpl(OnlineLoginClientTcpMessage message) throws Exception;
}
