package com.snowcattle.game.message.handler.messagehandler.common;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.message.handler.AbstractMessageHandler;
import com.snowcattle.game.message.logic.tcp.online.client.OnlineHeartClientTcpMessage;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.command.MessageCommandIndex;
import com.snowcattle.game.service.message.factory.TcpMessageFactory;

/**
 * Created by jiangwenping on 17/2/15.
 */
public class CommonHandlerImpl extends AbstractMessageHandler {

    @MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_HEART_CLIENT_TCP_MESSAGE)
    public AbstractNetMessage handleOnlineHeartMessage(OnlineHeartClientTcpMessage message) throws Exception {
        TcpMessageFactory messageFactory = LocalMananger.getInstance().getLocalSpringBeanManager().getTcpMessageFactory();
        return messageFactory.createCommonResponseMessage(message.getSerial());
    }
}
