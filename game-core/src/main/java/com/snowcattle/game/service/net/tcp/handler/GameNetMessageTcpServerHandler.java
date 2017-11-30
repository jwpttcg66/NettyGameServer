package com.snowcattle.game.service.net.tcp.handler;

import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.exception.GameHandlerException;
import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.update.service.UpdateService;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.event.GameAsyncEventService;
import com.snowcattle.game.service.event.impl.SessionRegisterEvent;
import com.snowcattle.game.service.event.impl.SessionUnRegisterEvent;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.message.factory.TcpMessageFactory;
import com.snowcattle.game.service.net.tcp.session.builder.NettyTcpSessionBuilder;
import com.snowcattle.game.service.update.NettyTcpSerssionUpdate;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.exception.NetMessageException;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.lookup.NetTcpSessionLoopUpService;
import com.snowcattle.game.service.net.tcp.pipeline.IServerPipeLine;
import com.snowcattle.game.service.net.tcp.session.NettyTcpSession;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;

/**
 * Created by jiangwenping on 17/2/7.
 * tcp协议处理handler
 */
public class GameNetMessageTcpServerHandler extends AbstractGameNetMessageTcpServerHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        AbstractNetProtoBufMessage netMessage = (AbstractNetProtoBufMessage) msg;
        //获取管道
        IServerPipeLine iServerPipeLine = LocalMananger.getInstance().getLocalSpringBeanManager().getDefaultTcpServerPipeLine();
        iServerPipeLine.dispatchAction(ctx.channel(), netMessage);
    }

    @Override
    public void addUpdateSession(NettyTcpSession nettyTcpSession){
        //加入到updateservice
        UpdateService updateService = LocalMananger.getInstance().getUpdateService();
        NettyTcpSerssionUpdate nettyTcpSerssionUpdate = new NettyTcpSerssionUpdate(nettyTcpSession);;
        EventParam<NettyTcpSerssionUpdate> param = new EventParam<NettyTcpSerssionUpdate>(nettyTcpSerssionUpdate);
        CycleEvent cycleEvent = new CycleEvent(Constants.EventTypeConstans.readyCreateEventType, nettyTcpSerssionUpdate.getUpdateId(), param);
        updateService.addReadyCreateEvent(cycleEvent);
    }

}
