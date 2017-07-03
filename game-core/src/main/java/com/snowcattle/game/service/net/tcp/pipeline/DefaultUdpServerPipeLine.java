package com.snowcattle.game.service.net.tcp.pipeline;

import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.constant.DynamicPropertiesEnum;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.logic.player.GamePlayer;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.lookup.GamePlayerLoopUpService;
import com.snowcattle.game.service.net.tcp.MessageAttributeEnum;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.AbstractNetProtoBufUdpMessage;
import com.snowcattle.game.service.message.command.MessageCommand;
import com.snowcattle.game.service.message.registry.MessageRegistry;
import com.snowcattle.game.service.net.tcp.process.GameUdpMessageOrderProcessor;
import com.snowcattle.game.service.net.tcp.process.GameUdpMessageProcessor;
import com.snowcattle.game.service.net.udp.NetUdpServerConfig;
import com.snowcattle.game.service.net.udp.session.NettyUdpSession;
import com.snowcattle.game.service.rpc.server.RpcServerRegisterConfig;
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
        AbstractNetProtoBufUdpMessage message = (AbstractNetProtoBufUdpMessage) abstractNetMessage;
        if (logger.isDebugEnabled()) {
            logger.debug("RECV_UDP_PROBUF_MESSAGE commandId :" + messageCommand.getCommand_id() + " class:" + abstractNetMessage.getClass().getSimpleName());
        }
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        //检查是否可以处理该消息
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();

        RpcServerRegisterConfig rpcServerRegisterConfig = gameServerConfigService.getRpcServerRegisterConfig();
        //如果是通用消息，不进行服务器检测
        if (!rpcServerRegisterConfig.validServer(messageCommand.bo_id)) {
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
        NetUdpServerConfig netUdpServerConfig = gameServerConfigService.getNetUdpServerConfig();
        if(netUdpServerConfig.getSdUdpServerConfig().isUdpMessageOrderQueueFlag()) {
            GameUdpMessageOrderProcessor gameUdpMessageOrderProcessor = LocalMananger.getInstance().getGameUdpMessageOrderProcessor();
            gameUdpMessageOrderProcessor.put(message);
        }else{
            GameUdpMessageProcessor gameUdpMessageProcessor = LocalMananger.getInstance().getGameUdpMessageProcessor();
            gameUdpMessageProcessor.put(message);
        }
    }
}
