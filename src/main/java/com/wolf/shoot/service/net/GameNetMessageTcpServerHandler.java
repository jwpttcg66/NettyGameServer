package com.wolf.shoot.service.net;

import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.update.service.UpdateService;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.exception.NetMessageException;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.lookup.NetTcpSessionLoopUpService;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufMessage;
import com.wolf.shoot.service.net.pipeline.IServerPipeLine;
import com.wolf.shoot.service.net.session.NettyTcpSession;
import com.wolf.shoot.service.net.session.builder.NettyTcpSessionBuilder;
import com.wolf.shoot.service.update.NettyTcpSerssionUpdate;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;

/**
 * Created by jiangwenping on 17/2/7.
 * tcp协议处理handler
 */
public class GameNetMessageTcpServerHandler extends ChannelInboundHandlerAdapter {

    public static Logger logger = Loggers.sessionLogger;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
        NettyTcpSessionBuilder nettyTcpSessionBuilder = LocalMananger.getInstance().getLocalSpringBeanManager().getNettyTcpSessionBuilder();
        NettyTcpSession nettyTcpSession = (NettyTcpSession) nettyTcpSessionBuilder.buildSession(ctx.channel());
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getNetTcpSessionLoopUpService();
        netTcpSessionLoopUpService.addNettySession(nettyTcpSession);

        //加入到updateservice
        UpdateService updateService = LocalMananger.getInstance().getUpdateService();
        NettyTcpSerssionUpdate nettyTcpSerssionUpdate = new NettyTcpSerssionUpdate(nettyTcpSession);;
        EventParam<NettyTcpSerssionUpdate> param = new EventParam<NettyTcpSerssionUpdate>(nettyTcpSerssionUpdate);
        CycleEvent cycleEvent = new CycleEvent(Constants.EventTypeConstans.readyCreateEventType, nettyTcpSerssionUpdate.getId(), param);
        updateService.addReadyCreateEvent(cycleEvent);

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractNetProtoBufMessage netMessage = (AbstractNetProtoBufMessage) msg;
        //获取管道
        IServerPipeLine iServerPipeLine = LocalMananger.getInstance().getLocalSpringBeanManager().getDefaultTcpServerPipeLine();
        iServerPipeLine.dispatchAction(ctx.channel(), netMessage);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        if (cause instanceof java.io.IOException)
            return;
        if(logger.isDebugEnabled()) {
            logger.debug("channel exceptionCaught", cause);
        }
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
            //因为updateService会自己删除，这里不需要逻辑
        }
        ctx.fireChannelUnregistered();
    }

    private void disconnect(Channel channel) throws NetMessageException {
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().getLocalSpringServiceManager().getNetTcpSessionLoopUpService();
        long sessonId = channel.attr(NettyTcpSessionBuilder.channel_sessionId).get();
        NettyTcpSession nettySession = (NettyTcpSession) netTcpSessionLoopUpService.lookup(sessonId);
        if (nettySession == null) {
            logger.error("tcp netsession null channelId is:" + channel.id().asLongText());
        }

        nettySession.close();
    }

}
