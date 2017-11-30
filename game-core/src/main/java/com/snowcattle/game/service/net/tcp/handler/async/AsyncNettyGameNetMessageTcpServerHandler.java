package com.snowcattle.game.service.net.tcp.handler.async;

import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.exception.GameHandlerException;
import com.snowcattle.game.common.exception.NetMessageException;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.logic.net.NetMessageProcessLogic;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.event.GameAsyncEventService;
import com.snowcattle.game.service.event.impl.SessionRegisterEvent;
import com.snowcattle.game.service.event.impl.SessionUnRegisterEvent;
import com.snowcattle.game.service.lookup.NetTcpSessionLoopUpService;
import com.snowcattle.game.service.net.tcp.MessageAttributeEnum;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.message.factory.TcpMessageFactory;
import com.snowcattle.game.service.net.tcp.handler.AbstractGameNetMessageTcpServerHandler;
import com.snowcattle.game.service.net.tcp.session.NettyTcpSession;
import com.snowcattle.game.service.net.tcp.session.builder.NettyTcpSessionBuilder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;

/**
 * Created by jiangwenping on 2017/5/22.
 * 使用AsyncNettyTcpHandlerService的handler
 *
 *  不会进行session的游戏内循环经查，断网后直接删除缓存，抛出掉线事件
 */
public class AsyncNettyGameNetMessageTcpServerHandler extends AbstractGameNetMessageTcpServerHandler {
    public static Logger logger = Loggers.sessionLogger;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractNetProtoBufMessage netMessage = (AbstractNetProtoBufMessage) msg;
        Channel channel = ctx.channel();
        //直接进行处理

        //装配session
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getNetTcpSessionLoopUpService();
        long sessonId = channel.attr(NettyTcpSessionBuilder.channel_session_id).get();
        NettyTcpSession nettySession = (NettyTcpSession) netTcpSessionLoopUpService.lookup(sessonId);
        if (nettySession == null) {
            logger.error("tcp netsession null channelId is:" + channel.id().asLongText());
            //已经丢失session， 停止处理
            return;
        }
        netMessage.setAttribute(MessageAttributeEnum.DISPATCH_SESSION, nettySession);

        //进行处理
        NetMessageProcessLogic netMessageProcessLogic = LocalMananger.getInstance().getLocalSpringBeanManager().getNetMessageProcessLogic();
        netMessageProcessLogic.processMessage(netMessage, nettySession);

    }
    @Override
    public   void addUpdateSession(NettyTcpSession nettyTcpSession){

    }
}
