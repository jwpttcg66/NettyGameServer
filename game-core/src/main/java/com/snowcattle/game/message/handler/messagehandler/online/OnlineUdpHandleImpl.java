package com.snowcattle.game.message.handler.messagehandler.online;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.message.handler.AbstractMessageHandler;
import com.snowcattle.game.message.logic.udp.online.OnlineHeartClientUDPMessage;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.command.MessageCommandIndex;

/**
 * Created by jiangwenping on 17/2/22.
 */
public class OnlineUdpHandleImpl extends AbstractMessageHandler {

    @MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_HEART_CLIENT_UDP_MESSAGE)
    public AbstractNetMessage handleOnlineHeartClientUdpMessage(OnlineHeartClientUDPMessage message) throws Exception {
        OnlineHeartClientUDPMessage onlineHeartClientUdpMessage = new OnlineHeartClientUDPMessage();
        onlineHeartClientUdpMessage.setId(Short.MAX_VALUE);
        long playerId = 6666;
        int tocken = 333;
        onlineHeartClientUdpMessage.setId(message.getId());
        onlineHeartClientUdpMessage.setPlayerId(playerId);
        onlineHeartClientUdpMessage.setTocken(tocken);
        onlineHeartClientUdpMessage.setReceive(message.getSend());
        return onlineHeartClientUdpMessage;
    }
}
