package com.wolf.shoot.message.handler.impl.online;

import com.wolf.shoot.message.handler.auto.online.OnlineUdpHandler;
import com.wolf.shoot.message.logic.udp.online.OnlineHeartClientUDPMessage;
import com.wolf.shoot.service.net.message.AbstractNetMessage;

/**
 * Created by jiangwenping on 17/2/22.
 */
public class OnlineUdpHandleImpl extends OnlineUdpHandler{
    @Override
    public AbstractNetMessage handleOnlineHeartClientUdpMessageImpl(OnlineHeartClientUDPMessage message) throws Exception {
        OnlineHeartClientUDPMessage onlineHeartClientUdpMessage = new OnlineHeartClientUDPMessage();
        onlineHeartClientUdpMessage.setId(Short.MAX_VALUE);
        long playerId = 6666;
        int tocken = 333;
        onlineHeartClientUdpMessage.setPlayerId(playerId);
        onlineHeartClientUdpMessage.setTocken(tocken);
        onlineHeartClientUdpMessage.setReceive(message.getSend());
        return onlineHeartClientUdpMessage;
    }
}
