package com.wolf.shoot.service.net.pipeline;

import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufUdpMessage;
import com.wolf.shoot.service.net.message.command.MessageCommand;
import com.wolf.shoot.service.net.message.registry.MessageRegistry;
import io.netty.channel.Channel;
import org.slf4j.Logger;

/**
 * Created by jwp on 2017/2/17.
 */
public class DefaultUdpServerPipeLine implements IServerPipeLine {
    public static Logger logger = Loggers.sessionLogger;

    @Override
    public void dispatchAction(Channel channel, AbstractNetMessage abstractNetMessage) {
        short commandId = abstractNetMessage.getNetMessageHead().getCmd();
        MessageRegistry messageRegistry = LocalMananger.getInstance().get(MessageRegistry.class);
        MessageCommand messageCommand = messageRegistry.getMessageCommand(commandId);
        if (logger.isDebugEnabled()) {
            logger.debug("RECV_UDP_PROBUF_MESSAGE:" + messageCommand.toString());
        }

//        AbstractNetProtoBufMessage abstractNetProtoBufMessage = (AbstractNetProtoBufMessage) abstractNetMessage;
//        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().get(NetTcpSessionLoopUpService.class);
//        NettyTcpSession nettySession = (NettyTcpSession) netTcpSessionLoopUpService.lookup(channel.id().asLongText());
//        if (nettySession == null) {
//            logger.error("netsession null channelId is:" + channel.id().asLongText());
//        }
//        abstractNetProtoBufMessage.setSessionId(nettySession.getSessionId());

        //检查是否可以处理该消息
        GameServerConfig gameServerConfig = LocalMananger.getInstance().getGameServerConfigService().getGameServerConfig();

        //如果是通用消息，不进行服务器检测
//        if (gameServerConfig.getServerType() != messageCommands.bo_id && !messageCommands.isIs_common()) {
//            if (nettySession.getPlayerId() != 0) {
//                logger.debug("discard udp message  sessionId:" + nettySession.getSessionId() + " messageId is " + commandId);
//            } else {
//                logger.debug("discard udp message  playerId:" + nettySession.getPlayerId() + " messageId is " + commandId);
//            }
//
//            return;
//        }

//        if (gameServerConfig.isDevelopModel() && logger.isDebugEnabled()) {
//            logger.debug("sessionId" + nettySession.getSessionId() + " playerId" + nettySession.getPlayerId() + " read message" + commandId + "info" + abstractNetProtoBufMessage.toAllInfoString());
//        }
        AbstractNetProtoBufUdpMessage message = (AbstractNetProtoBufUdpMessage) abstractNetMessage;
        int serial = abstractNetMessage.getSerial();
        long playerId = message.getPlayerId();
        int tocken = message.getTocken();
        if (messageCommand.is_need_filter()) {
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
//        abstractNetMessage.setAttribute(MessageAttributeEnum.DISPATCH_SESSION, nettySession);
//        GameTcpMessageProcessor gameTcpMessageProcessor = (GameTcpMessageProcessor) LocalMananger.getInstance().get(IMessageProcessor.class);
//        if(gameServerConfig.isMessageQueueDirectDispatch()){
//            gameTcpMessageProcessor.directPutTcpMessage(abstractNetMessage);
//        }else{
//            gameTcpMessageProcessor.put(abstractNetProtoBufMessage);
//        }
    }
}
