package com.wolf.shoot.message.handler.impl.online;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.logic.player.GamePlayer;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.message.handler.auto.online.OnlineHandler;
import com.wolf.shoot.message.logic.tcp.online.client.OnlineLoginClientTcpMessage;
import com.wolf.shoot.message.logic.tcp.online.server.OnlineLoginServerTcpMessage;
import com.wolf.shoot.service.lookup.GamePlayerLoopUpService;
import com.wolf.shoot.service.net.MessageAttributeEnum;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.session.NettyTcpSession;

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
        NettyTcpSession clientSesion = (NettyTcpSession) message.getAttribute(MessageAttributeEnum.DISPATCH_SESSION);
        GamePlayer gamePlayer = new GamePlayer(clientSesion, playerId, tocken);
        GamePlayerLoopUpService gamePlayerLoopUpService = LocalMananger.getInstance().get(GamePlayerLoopUpService.class);
        gamePlayerLoopUpService.addT(gamePlayer);
        return onlineLoginServerTcpMessage;
    }
}
