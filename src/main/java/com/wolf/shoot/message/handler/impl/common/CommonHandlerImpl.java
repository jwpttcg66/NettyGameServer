package com.wolf.shoot.message.handler.impl.common;

import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.message.handler.auto.common.CommonHandler;
import com.wolf.shoot.message.logic.tcp.online.client.OnlineHeartClientTcpMessage;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.factory.TcpMessageFactory;

/**
 * Created by jiangwenping on 17/2/15.
 */
public class CommonHandlerImpl extends CommonHandler{
    @Override
    public AbstractNetMessage handleOnlineHeartMessageImpl(OnlineHeartClientTcpMessage message) throws Exception {
        TcpMessageFactory messageFactory = LocalMananger.getInstance().getLocalSpringBeanManager().getTcpMessageFactory();
        return messageFactory.createCommonResponseMessage(message.getSerial());
    }
}
