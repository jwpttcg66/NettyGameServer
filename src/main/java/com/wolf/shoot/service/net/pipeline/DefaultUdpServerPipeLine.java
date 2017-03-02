package com.wolf.shoot.service.net.pipeline;

import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.DynamicPropertiesEnum;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.logic.player.GamePlayer;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.lookup.GamePlayerLoopUpService;
import com.wolf.shoot.service.net.MessageAttributeEnum;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufUdpMessage;
import com.wolf.shoot.service.net.message.command.MessageCommand;
import com.wolf.shoot.service.net.message.command.MessageCommandEnum;
import com.wolf.shoot.service.net.message.registry.MessageRegistry;
import com.wolf.shoot.service.net.process.GameUdpMessageProcessor;
import com.wolf.shoot.service.net.session.NettyUdpSession;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/2/17.
 * udp协议暂时假定不需要返回数据
 */
@Service
public class DefaultUdpServerPipeLine implements IServerPipeLine {
    public static Logger logger = Loggers.sessionLogger;

    @Override
    public void dispatchAction(Channel channel, AbstractNetMessage abstractNetMessage) {
        short commandId = abstractNetMessage.getNetMessageHead().getCmd();
        MessageRegistry messageRegistry = LocalMananger.getInstance().getLocalSpringServiceManager().getMessageRegistry();
        MessageCommand messageCommand = messageRegistry.getMessageCommand(commandId);
        if (logger.isDebugEnabled()) {
            logger.debug("RECV_UDP_PROBUF_MESSAGE:" + MessageCommandEnum.values()[commandId]);
        }

        AbstractNetProtoBufUdpMessage message = (AbstractNetProtoBufUdpMessage) abstractNetMessage;
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        //检查是否可以处理该消息
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();

        //如果是通用消息，不进行服务器检测
        if (gameServerConfig.getServerType() != messageCommand.bo_id && !messageCommand.is_common()) {
            if (logger.isDebugEnabled()) {
                logger.debug("discard udp message  playerId:" + message.getPlayerId() + " messageId is " + commandId);
            }
            return;
        }

        if (gameServerConfig.isDevelopModel()){
            if (logger.isDebugEnabled()) {
                logger.debug(" playerId" + message.getPlayerId() + " read message" + commandId + "info" + message.toAllInfoString());
            }
        }

        int serial = abstractNetMessage.getSerial();
        long playerId = message.getPlayerId();
        int tocken = message.getTocken();
        boolean checkFlag = gameServerConfigService.getGameDynamicPropertiesConfig().getProperty(DynamicPropertiesEnum.udp_message_tocken_check_flag.toString(), false);
        if (messageCommand.is_need_filter() && checkFlag) {
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
            GamePlayerLoopUpService gamePlayerLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getGamePlayerLoopUpService();
            GamePlayer gamePlayer = gamePlayerLoopUpService.lookup(playerId);
            if (gamePlayer == null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("player not exsit discard udp message  playerId:" + message.getPlayerId() + " messageId is " + commandId);
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
        //TODO 优化UDPsession
        message.setAttribute(MessageAttributeEnum.DISPATCH_SESSION, new NettyUdpSession(channel));
        GameUdpMessageProcessor gameUdpMessageProcessor = (GameUdpMessageProcessor) LocalMananger.getInstance().get(GameUdpMessageProcessor.class);
        gameUdpMessageProcessor.put(message);
    }
}
