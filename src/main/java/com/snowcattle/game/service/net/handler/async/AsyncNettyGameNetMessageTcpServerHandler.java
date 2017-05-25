package com.snowcattle.game.service.net.handler.async;

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.exception.NetMessageException;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.logic.net.NetMessageProcessLogic;
import com.snowcattle.game.manager.LocalMananger;
import com.snowcattle.game.service.event.GameAsyncEventService;
import com.snowcattle.game.service.event.SingleEventConstants;
import com.snowcattle.game.service.event.impl.SessionRegisterEvent;
import com.snowcattle.game.service.event.impl.SessionUnRegisterEvent;
import com.snowcattle.game.service.lookup.NetTcpSessionLoopUpService;
import com.snowcattle.game.service.net.MessageAttributeEnum;
import com.snowcattle.game.service.net.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.net.session.NettyTcpSession;
import com.snowcattle.game.service.net.session.builder.NettyTcpSessionBuilder;
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
public class AsyncNettyGameNetMessageTcpServerHandler extends ChannelInboundHandlerAdapter {
    public static Logger logger = Loggers.sessionLogger;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
        NettyTcpSessionBuilder nettyTcpSessionBuilder = LocalMananger.getInstance().getLocalSpringBeanManager().getNettyTcpSessionBuilder();
        NettyTcpSession nettyTcpSession = (NettyTcpSession) nettyTcpSessionBuilder.buildSession(ctx.channel());
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getNetTcpSessionLoopUpService();
        netTcpSessionLoopUpService.addNettySession(nettyTcpSession);

        //生成aysnc事件
        long sessionId = nettyTcpSession.getSessionId();
        EventParam<NettyTcpSession> sessionEventParam = new EventParam<>(nettyTcpSession);
        SessionRegisterEvent sessionRegisterEvent = new SessionRegisterEvent(sessionId, sessionId, sessionEventParam);
        GameAsyncEventService gameAsyncEventService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameAsyncEventService();
        gameAsyncEventService.putEvent(sessionRegisterEvent);
    }


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
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws NetMessageException {
        // Close the connection when an exception is raised.
        if (cause instanceof java.io.IOException)
            return;
        if(logger.isDebugEnabled()) {
            logger.debug("channel exceptionCaught", cause);
        }

        //设置下线
        disconnect(ctx.channel());

        //销毁上下文
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object var) throws Exception{
        super.userEventTriggered(ctx, var);
        if(var instanceof IdleStateEvent){
            //说明是空闲事件
            disconnect(ctx.channel());
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getNetTcpSessionLoopUpService();
        long sessonId = ctx.channel().attr(NettyTcpSessionBuilder.channel_session_id).get();
        NettyTcpSession nettyTcpSession = (NettyTcpSession) netTcpSessionLoopUpService.lookup(sessonId);
        if(nettyTcpSession != null) {
            netTcpSessionLoopUpService.removeNettySession(nettyTcpSession);
        }

        //生成aysnc事件
        long sessionId = nettyTcpSession.getSessionId();
        EventParam<NettyTcpSession> sessionEventParam = new EventParam<>(nettyTcpSession);
        SessionUnRegisterEvent sessionUnRegisterEvent = new SessionUnRegisterEvent(sessionId, sessionId, sessionEventParam);
        GameAsyncEventService gameAsyncEventService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameAsyncEventService();
        gameAsyncEventService.putEvent(sessionUnRegisterEvent);

        ctx.fireChannelUnregistered();
    }

    private void disconnect(Channel channel) throws NetMessageException {
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getNetTcpSessionLoopUpService();
        long sessonId = channel.attr(NettyTcpSessionBuilder.channel_session_id).get();
        NettyTcpSession nettyTcpSession = (NettyTcpSession) netTcpSessionLoopUpService.lookup(sessonId);
        if (nettyTcpSession == null) {
            logger.error("tcp netsession null channelId is:" + channel.id().asLongText());
            return;
        }

        nettyTcpSession.close();

    }
}
