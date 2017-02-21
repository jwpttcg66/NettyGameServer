package com.wolf.shoot.message.handler.impl.online;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.message.handler.auto.online.OnlineHandler;
import com.wolf.shoot.message.logic.tcp.online.client.OnlineLoginClientTcpMessage;
import com.wolf.shoot.message.logic.tcp.online.server.OnlineLoginServerTcpMessage;
import com.wolf.shoot.service.net.message.AbstractNetMessage;

/**
 * Created by jiangwenping on 17/2/21.
 */
public class OnlineHandlerImpl extends OnlineHandler{

    @Override
    public AbstractNetMessage handleOnlineLoginClientTcpMessageImpl(OnlineLoginClientTcpMessage message) throws Exception {
        OnlineLoginServerTcpMessage onlineLoginServerTcpMessage = new OnlineLoginServerTcpMessage();
        long playerId = 6666;
        int tocken = 333;
        onlineLoginServerTcpMessage.setPlayerId(playerId);
        onlineLoginServerTcpMessage.setTocken(tocken);
        if (Loggers.sessionLogger.isDebugEnabled()) {
            Loggers.sessionLogger.debug( "playerId " + playerId + "tocken " + tocken + "login");
        }
        return onlineLoginServerTcpMessage;
    }
}
