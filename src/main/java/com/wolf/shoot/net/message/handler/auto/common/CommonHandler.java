package com.wolf.shoot.net.message.handler.auto.common;

import com.wolf.shoot.common.annotation.MessageCommandAnnotation;
import com.wolf.shoot.net.message.MessageCommands;
import com.wolf.shoot.net.message.AbstractNetMessage;
import com.wolf.shoot.net.message.handler.AbstractMessageHandler;
import com.wolf.shoot.net.message.logic.tcp.online.OnlineHeartMessageAbstract;

/**
 * Created by jiangwenping on 17/2/15.
 */
public abstract class CommonHandler extends AbstractMessageHandler{
    @MessageCommandAnnotation(command = MessageCommands.ONLINE_HEART_MESSAGE)
    public AbstractNetMessage handleOnlineHeartHeartMessage(OnlineHeartMessageAbstract message) throws Exception{
        return handleOnlineHeartMessageImpl(message);
    }

    public abstract AbstractNetMessage handleOnlineHeartMessageImpl(OnlineHeartMessageAbstract message) throws Exception;
}
