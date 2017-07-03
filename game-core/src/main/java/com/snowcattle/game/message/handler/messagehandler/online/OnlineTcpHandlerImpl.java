package com.snowcattle.game.message.handler.messagehandler.online;

import com.snowcattle.game.common.annotation.MessageCommandAnnotation;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.logic.player.GamePlayer;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.message.handler.AbstractMessageHandler;
import com.snowcattle.game.message.logic.tcp.online.client.OnlineLoginClientTcpMessage;
import com.snowcattle.game.message.logic.tcp.online.server.OnlineLoginServerTcpMessage;
import com.snowcattle.game.service.lookup.GamePlayerLoopUpService;
import com.snowcattle.game.service.net.tcp.MessageAttributeEnum;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.command.MessageCommandIndex;
import com.snowcattle.game.service.net.tcp.session.NettyTcpSession;

/**
 * Created by jiangwenping on 17/2/21.
 */
public class OnlineTcpHandlerImpl extends AbstractMessageHandler {

    @MessageCommandAnnotation(command = MessageCommandIndex.ONLINE_LOGIN_TCP_CLIENT_MESSAGE)
    public AbstractNetMessage handleOnlineLoginClientTcpMessage(OnlineLoginClientTcpMessage message) throws Exception {
        OnlineLoginServerTcpMessage onlineLoginServerTcpMessage = new OnlineLoginServerTcpMessage();
        long playerId = 6666;
        int tocken = 333;
        onlineLoginServerTcpMessage.setPlayerId(playerId);
        onlineLoginServerTcpMessage.setTocken(tocken);
        if (Loggers.sessionLogger.isDebugEnabled()) {
            Loggers.sessionLogger.debug( "playerId " + playerId + "tocken " + tocken + "login");
        }
        NettyTcpSession clientSesion = (NettyTcpSession) message.getAttribute(MessageAttributeEnum.DISPATCH_SESSION);
        GamePlayer gamePlayer = new GamePlayer(clientSesion.getNettyTcpNetMessageSender(), playerId, tocken);
        GamePlayerLoopUpService gamePlayerLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getGamePlayerLoopUpService();
        gamePlayerLoopUpService.addT(gamePlayer);
        return onlineLoginServerTcpMessage;
    }
}
