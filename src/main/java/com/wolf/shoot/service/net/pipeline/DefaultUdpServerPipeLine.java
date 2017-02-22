package com.wolf.shoot.service.net.pipeline;

import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.constant.BOConst;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.logic.player.GamePlayer;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.lookup.GamePlayerLoopUpService;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufUdpMessage;
import com.wolf.shoot.service.net.message.command.MessageCommand;
import com.wolf.shoot.service.net.message.registry.MessageRegistry;
import io.netty.channel.Channel;
import org.slf4j.Logger;

/**
 * Created by jwp on 2017/2/17.
 *  udp协议暂时假定不需要返回数据
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

        AbstractNetProtoBufUdpMessage message = (AbstractNetProtoBufUdpMessage) abstractNetMessage;
        //检查是否可以处理该消息
        GameServerConfig gameServerConfig = LocalMananger.getInstance().getGameServerConfigService().getGameServerConfig();

        //如果是通用消息，不进行服务器检测
        if (gameServerConfig.getServerType() != messageCommand.bo_id && !messageCommand.is_common()) {
            logger.debug("discard udp message  playerId:" + message.getPlayerId() + " messageId is " + commandId);
            return;
        }

        if (gameServerConfig.isDevelopModel() && logger.isDebugEnabled()) {
            logger.debug( " playerId" + message.getPlayerId() + " read message" + commandId + "info" + message.toAllInfoString());
        }

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
            GamePlayerLoopUpService gamePlayerLoopUpService = LocalMananger.getInstance().get(GamePlayerLoopUpService.class);
            GamePlayer gamePlayer = gamePlayerLoopUpService.lookup(playerId);
            if(gamePlayer == null){
                if(gameServerConfig.getServerType() == BOConst.BO_WORLD){
                    
                }
                return;
            }
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
