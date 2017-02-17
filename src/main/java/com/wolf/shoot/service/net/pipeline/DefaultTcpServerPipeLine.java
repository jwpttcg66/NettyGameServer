package com.wolf.shoot.service.net.pipeline;

import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.net.message.MessageCommands;
import com.wolf.shoot.net.message.NetMessage;
import com.wolf.shoot.net.message.NetProtoBufMessage;
import com.wolf.shoot.net.message.registry.MessageRegistry;
import com.wolf.shoot.net.session.NettySession;
import com.wolf.shoot.net.session.NettyTcpSession;
import com.wolf.shoot.service.lookup.NetTcpSessionLoopUpService;
import com.wolf.shoot.service.net.MessageAttributeEnum;
import com.wolf.shoot.service.net.process.GameMessageProcessor;
import com.wolf.shoot.service.net.process.IMessageProcessor;
import io.netty.channel.Channel;
import org.slf4j.Logger;

/**
 * Created by jiangwenping on 17/2/13.
 * 处理管道
 */
public class DefaultTcpServerPipeLine implements ITcpServerPipeLine {
    public static Logger logger = Loggers.sessionLogger;

    @Override
    public void dispatchAction(Channel channel, NetMessage netMessage) {
        short commandId = netMessage.getNetMessageHead().getCmd();
        MessageRegistry messageRegistry = LocalMananger.getInstance().get(MessageRegistry.class);
        MessageCommands messageCommands = messageRegistry.getMessageCommand(commandId);
        if (logger.isDebugEnabled()) {
            logger.debug("RECV_PROBUF_MESSAGE:" + messageCommands.toString());
        }

        NetProtoBufMessage netProtoBufMessage = (NetProtoBufMessage) netMessage;
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().get(NetTcpSessionLoopUpService.class);
        NettyTcpSession nettySession = (NettyTcpSession) netTcpSessionLoopUpService.lookup(channel.id().asLongText());
        if (nettySession == null) {
            logger.error("netsession null channelId is:" + channel.id().asLongText());
        }

        netProtoBufMessage.setSessionId(nettySession.getSessionId());

        //检查是否可以处理该消息
        GameServerConfig gameServerConfig = LocalMananger.getInstance().getGameServerConfigService().getGameServerConfig();

        //如果是通用消息，不进行服务器检测
        if (gameServerConfig.getServerType() != messageCommands.bo_id && !messageCommands.isIs_common()) {
            if (nettySession.getPlayerId() != 0) {
                logger.debug("discard message  sessionId:" + nettySession.getSessionId() + " messageId is " + commandId);
            } else {
                logger.debug("discard message  playerId:" + nettySession.getPlayerId() + " messageId is " + commandId);
            }

            return;
        }

        if (gameServerConfig.isDevelopModel() && logger.isDebugEnabled()) {
            logger.debug("sessionId" + nettySession.getSessionId() + " playerId" + nettySession.getPlayerId() + " read message" + commandId + "info" + netProtoBufMessage.toAllInfoString());
        }

        if (messageCommands.isIs_need_filter()) {
            int serial = netMessage.getSerial();
            long playerId = nettySession.getPlayerId();
//            PlatformType platformType = nettySession.getPlatformType();
//            if(platformType == null){
//                AbstractGameMessage response = GameUtils.errorCallMessage(message.getCommandId(), serial, MessageErrorEnum.COMMON_MESSAGE_PLATFROM_NO_EXIST);
//                if(gameServerConfig.getServerType() == BOConst.BO_GAME){
//                    response = GameUtils.errorCallMessage(message.getCommandId(), serial, MessageErrorEnum.GAME_ERROR_COMMON_MESSAGE_PLATFROM_NO_EXIST);
//                }
//                nettySession.write(response);
//                return;
//
//            }

//            A5Player a5Player = ObjectAccessorEx.getA5Player(playerId, platformType);
//            if(a5Player == null){
//                AbstractGameMessage response = GameUtils.errorCallMessage(message.getCommandId(), serial, MessageErrorEnum.COMMON_MESSAGE_PLAYER_NO_EXIST);
//                if(gameServerConfig.getServerType() == BOConst.BO_GAME){
//                    response = GameUtils.errorCallMessage(message.getCommandId(), serial, MessageErrorEnum.GAME_ERROR_COMMON_MESSAGE_PLAYER_NO_EXIST);
//                }
//                clientSession.write(response);
//                return;
//            }
//
//            if(a5Player.getMsgSerial() == serial){
//                AbstractGameMessage response = GameUtils.errorCallMessage(message.getCommandId(), serial, MessageErrorEnum.COMMON_MESSAGE_ALREADY_HANDLED);
//                clientSession.write(response);
//                return;
//            }
        }

//        //放入处理队列
        netMessage.setAttribute(MessageAttributeEnum.DISPATCH_SESSION, nettySession);
        GameMessageProcessor gameMessageProcessor = (GameMessageProcessor) LocalMananger.getInstance().get(IMessageProcessor.class);
        if(gameServerConfig.isMessageQueueDirectDispatch()){
            gameMessageProcessor.directPut(netMessage);
        }else{
           gameMessageProcessor.put(netProtoBufMessage);
        }
    }
}
