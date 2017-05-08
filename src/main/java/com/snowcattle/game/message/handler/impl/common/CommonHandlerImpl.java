package com.snowcattle.game.message.handler.impl.common;

import com.snowcattle.game.manager.LocalMananger;
import com.snowcattle.game.message.handler.auto.common.CommonHandler;
import com.snowcattle.game.message.logic.tcp.online.client.OnlineHeartClientTcpMessage;
import com.snowcattle.game.service.net.message.AbstractNetMessage;
import com.snowcattle.game.service.net.message.factory.TcpMessageFactory;

/**
 * Created by jiangwenping on 17/2/15.
 */
public class CommonHandlerImpl extends CommonHandler {
    @Override
    public AbstractNetMessage handleOnlineHeartMessageImpl(OnlineHeartClientTcpMessage message) throws Exception {
        TcpMessageFactory messageFactory = LocalMananger.getInstance().getLocalSpringBeanManager().getTcpMessageFactory();
        return messageFactory.createCommonResponseMessage(message.getSerial());
    }
}
