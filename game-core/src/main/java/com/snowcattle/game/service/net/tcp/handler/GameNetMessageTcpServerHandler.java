package com.snowcattle.game.service.net.tcp.handler;

import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.update.service.UpdateService;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.update.NettyTcpSerssionUpdate;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.net.tcp.pipeline.IServerPipeLine;
import com.snowcattle.game.service.net.tcp.session.NettyTcpSession;
import io.netty.channel.ChannelHandlerContext;

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
