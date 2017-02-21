package com.wolf.shoot.message.handler.auto.common;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.message.handler.AbstractMessageHandler;
import com.wolf.shoot.message.logic.tcp.online.client.OnlineHeartClientMessage;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.command.MessageCommandIndex;


/**
 * Created by jiangwenping on 17/2/15.
 */
public abstract class CommonHandler extends AbstractMessageHandler {
    @MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_HEART_MESSAGE)
    public AbstractNetMessage handleOnlineHeartHeartMessage(OnlineHeartClientMessage message) throws Exception{
        return handleOnlineHeartMessageImpl(message);
    }

    public abstract AbstractNetMessage handleOnlineHeartMessageImpl(OnlineHeartClientMessage message) throws Exception;
}
