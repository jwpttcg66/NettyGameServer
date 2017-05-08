package com.snowcattle.game.message.handler.impl.online;

import com.snowcattle.game.manager.LocalMananger;
import com.snowcattle.game.service.lookup.GamePlayerLoopUpService;
import com.snowcattle.game.service.net.MessageAttributeEnum;
import com.snowcattle.game.service.net.message.AbstractNetMessage;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.logic.player.GamePlayer;
import com.snowcattle.game.message.handler.auto.online.OnlineTcpHandler;
import com.snowcattle.game.message.logic.tcp.online.client.OnlineLoginClientTcpMessage;
import com.snowcattle.game.message.logic.tcp.online.server.OnlineLoginServerTcpMessage;
import com.snowcattle.game.service.net.session.NettyTcpSession;

/**
 * Created by jiangwenping on 17/2/21.
 */
public class OnlineTcpHandlerImpl extends OnlineTcpHandler {

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
        GamePlayerLoopUpService gamePlayerLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getGamePlayerLoopUpService();
        gamePlayerLoopUpService.addT(gamePlayer);
        return onlineLoginServerTcpMessage;
    }
}
