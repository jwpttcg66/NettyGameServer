package com.wolf.shoot.message.handler.impl;

import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.message.handler.auto.common.CommonHandler;
import com.wolf.shoot.message.logic.tcp.online.client.OnlineHeartClientMessage;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.factory.IMessageFactory;
import com.wolf.shoot.service.net.message.factory.MessageFactory;

/**
 * Created by jiangwenping on 17/2/15.
 */
public class CommonHandleImpl extends CommonHandler{
    @Override
    public AbstractNetMessage handleOnlineHeartMessageImpl(OnlineHeartClientMessage message) throws Exception {
        MessageFactory messageFactory = (MessageFactory) LocalMananger.getInstance().get(IMessageFactory.class);
        return messageFactory.createCommonResponseMessage(message.getSerial());
    }
}
