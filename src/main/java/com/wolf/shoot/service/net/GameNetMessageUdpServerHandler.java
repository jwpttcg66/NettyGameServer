package com.wolf.shoot.service.net;

import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufUdp2Message;
import com.wolf.shoot.service.net.pipeline.DefaultUdpServerPipeLine;
import com.wolf.shoot.service.net.pipeline.IServerPipeLine;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by jwp on 2017/2/17.
 */
public class GameNetMessageUdpServerHandler extends SimpleChannelInboundHandler<AbstractNetProtoBufUdp2Message> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AbstractNetProtoBufUdp2Message netMessage) throws Exception {
//        System.out.println(netMessage);
//        if(netMessage instanceof OnlineHeartUDP2Message){
//            OnlineHeartUDP2Message onlineHeartUDPMessage = new OnlineHeartUDP2Message();
//            onlineHeartUDPMessage.setId(Short.MAX_VALUE);
//            onlineHeartUDPMessage.setReceive(((OnlineHeartUDP2Message) netMessage).getSend());
//            channelHandlerContext.writeAndFlush(onlineHeartUDPMessage).sync();
//        }
        //获取管道
        IServerPipeLine iServerPipeLine = LocalMananger.getInstance().get(DefaultUdpServerPipeLine.class);
        iServerPipeLine.dispatchAction(channelHandlerContext.channel(), netMessage);

    }

}
