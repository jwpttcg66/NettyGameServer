package com.wolf.shoot.service.net;

import com.snowcattle.game.excutor.event.CycleEvent;
import com.snowcattle.game.excutor.event.EventParam;
import com.snowcattle.game.excutor.service.UpdateService;
import com.snowcattle.game.excutor.utils.Constants;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.net.message.NetProtoBufMessage;
import com.wolf.shoot.net.session.NettyTcpSession;
import com.wolf.shoot.net.session.builder.NettyTcpSessionBuilder;
import com.wolf.shoot.service.lookup.NetTcpSessionLoopUpService;
import com.wolf.shoot.service.net.pipeline.DefaultTcpServerPipeLine;
import com.wolf.shoot.service.net.pipeline.ITcpServerPipeLine;
import com.wolf.shoot.service.update.NettyTcpSerssionUpdate;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by jiangwenping on 17/2/7.
 */
public class GameNetMessageTcpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
        NettyTcpSessionBuilder nettyTcpSessionBuilder = LocalMananger.getInstance().get(NettyTcpSessionBuilder.class);
        NettyTcpSession nettyTcpSession = (NettyTcpSession) nettyTcpSessionBuilder.buildSession(ctx.channel());
        NetTcpSessionLoopUpService netTcpSessionLoopUpService = LocalMananger.getInstance().get(NetTcpSessionLoopUpService.class);
        netTcpSessionLoopUpService.addNettySession(nettyTcpSession);

        //加入到updateservice
        UpdateService updateService = LocalMananger.getInstance().get(UpdateService.class);
        NettyTcpSerssionUpdate nettyTcpSerssionUpdate = new NettyTcpSerssionUpdate(nettyTcpSession);;
        EventParam<NettyTcpSerssionUpdate> param = new EventParam<NettyTcpSerssionUpdate>(nettyTcpSerssionUpdate);
        CycleEvent cycleEvent = new CycleEvent(Constants.EventTypeConstans.readyCreateEventType, nettyTcpSerssionUpdate.getId(), param);
        updateService.addReadyCreateEvent(cycleEvent);

    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NetProtoBufMessage netMessage = (NetProtoBufMessage) msg;
//        if(netMessage instanceof OnlineHeartMessage){
//            OnlineHeartMessage onlineHeartMessage = (OnlineHeartMessage) netMessage;
//            System.out.println("服务端收到 OnlineHeartMessage id：" + onlineHeartMessage.getId());
//        }
        //获取管道
        ITcpServerPipeLine iTcpServerPipeLine = LocalMananger.getInstance().get(DefaultTcpServerPipeLine.class);
        iTcpServerPipeLine.dispatchAction(ctx.channel(), netMessage);

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object var) throws Exception{
        super.userEventTriggered(ctx, var);
        if(var instanceof IdleStateEvent){
            //说明是空闲事件
            ctx.close();
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
    }

}
