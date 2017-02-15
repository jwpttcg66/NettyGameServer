package com.wolf.shoot.net.message.handler.auto.common;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.net.message.MessageCommands;
import com.wolf.shoot.net.message.NetMessage;
import com.wolf.shoot.net.message.handler.AbstractMessageHandler;
import com.wolf.shoot.net.message.logic.common.OnlineHeartMessage;

/**
 * Created by jiangwenping on 17/2/15.
 */
public abstract class CommonHandler extends AbstractMessageHandler{
    @MessageCommandAnnotation(command = MessageCommands.ONLINE_HEART_MESSAGE)
    public NetMessage handleOnlineHeartHeartMessage(OnlineHeartMessage message) throws Exception{
        return handleOnlineHeartMessageImpl(message);
    }

    public abstract NetMessage handleOnlineHeartMessageImpl(OnlineHeartMessage message) throws Exception;
}
