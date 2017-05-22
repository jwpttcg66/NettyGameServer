package com.snowcattle.game.service.net.handler.async;

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.exception.NetMessageException;
import com.snowcattle.game.manager.LocalMananger;
import com.snowcattle.game.service.lookup.NetTcpSessionLoopUpService;
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

//        //加入到updateservice
//        UpdateService updateService = LocalMananger.getInstance().getUpdateService();
//        NettyTcpSerssionUpdate nettyTcpSerssionUpdate = new NettyTcpSerssionUpdate(nettyTcpSession);;
//        EventParam<NettyTcpSerssionUpdate> param = new EventParam<NettyTcpSerssionUpdate>(nettyTcpSerssionUpdate);
//        CycleEvent cycleEvent = new CycleEvent(Constants.EventTypeConstans.readyCreateEventType, nettyTcpSerssionUpdate.getId(), param);
//        updateService.addReadyCreateEvent(cycleEvent);

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        AbstractNetProtoBufMessage netMessage = (AbstractNetProtoBufMessage) msg;
//        //获取管道
//        IServerPipeLine iServerPipeLine = LocalMananger.getInstance().getLocalSpringBeanManager().getDefaultTcpServerPipeLine();
//        iServerPipeLine.dispatchAction(ctx.channel(), netMessage);
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
        long sessonId = ctx.channel().attr(NettyTcpSessionBuilder.channel_sessionId).get();
        NettyTcpSession nettyTcpSession = (NettyTcpSession) netTcpSessionLoopUpService.lookup(sessonId);
        if(nettyTcpSession != null) {
            netTcpSessionLoopUpService.removeNettySession(nettyTcpSession);
        }
        ctx.fireChannelUnregistered();
    }

    private void disconnect(Channel channel) throws NetMessageException {
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getNetTcpSessionLoopUpService();
        long sessonId = channel.attr(NettyTcpSessionBuilder.channel_sessionId).get();
        NettyTcpSession nettySession = (NettyTcpSession) netTcpSessionLoopUpService.lookup(sessonId);
        if (nettySession == null) {
            logger.error("tcp netsession null channelId is:" + channel.id().asLongText());
            return;
        }

        nettySession.close();
    }
}
