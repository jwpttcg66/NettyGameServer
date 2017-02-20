package com.wolf.shoot.service.net;

import com.wolf.shoot.service.net.message.AbstractNetProtoBufMessage;
import com.wolf.shoot.message.logic.udp.online.OnlineHeartUDPMessageAbstract;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by jwp on 2017/2/17.
 */
public class GameNetMessageUdpServerHandler extends SimpleChannelInboundHandler<AbstractNetProtoBufMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AbstractNetProtoBufMessage netMessage) throws Exception {
        System.out.println(netMessage);
        if(netMessage instanceof OnlineHeartUDPMessageAbstract){
            OnlineHeartUDPMessageAbstract onlineHeartUDPMessage = new OnlineHeartUDPMessageAbstract();
            onlineHeartUDPMessage.setId(Short.MAX_VALUE);
            onlineHeartUDPMessage.setReceive(((OnlineHeartUDPMessageAbstract) netMessage).getSend());
            channelHandlerContext.writeAndFlush(onlineHeartUDPMessage).sync();
        }
    }

}
