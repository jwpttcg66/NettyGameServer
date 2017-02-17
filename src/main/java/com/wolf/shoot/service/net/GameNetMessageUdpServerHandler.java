package com.wolf.shoot.service.net;

import com.wolf.shoot.net.message.AbstractAbstractNetProtoBufMessage;
import com.wolf.shoot.net.message.logic.udp.online.OnlineHeartUDPMessageAbstract;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by jwp on 2017/2/17.
 */
public class GameNetMessageUdpServerHandler extends SimpleChannelInboundHandler<AbstractAbstractNetProtoBufMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AbstractAbstractNetProtoBufMessage netMessage) throws Exception {
        System.out.println(netMessage);
        if(netMessage instanceof OnlineHeartUDPMessageAbstract){
            OnlineHeartUDPMessageAbstract onlineHeartUDPMessage = new OnlineHeartUDPMessageAbstract();
            onlineHeartUDPMessage.setId(Short.MAX_VALUE);
            onlineHeartUDPMessage.setReceive(((OnlineHeartUDPMessageAbstract) netMessage).getSend());
            channelHandlerContext.writeAndFlush(onlineHeartUDPMessage).sync();
        }
    }

}
