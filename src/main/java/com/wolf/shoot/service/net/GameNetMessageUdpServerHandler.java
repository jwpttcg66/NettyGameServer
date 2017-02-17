package com.wolf.shoot.service.net;

import com.wolf.shoot.net.message.NetProtoBufMessage;
import com.wolf.shoot.net.message.logic.udp.online.OnlineHeartUDPMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by jwp on 2017/2/17.
 */
public class GameNetMessageUdpServerHandler extends SimpleChannelInboundHandler<NetProtoBufMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, NetProtoBufMessage netMessage) throws Exception {
        System.out.println(netMessage);
        if(netMessage instanceof OnlineHeartUDPMessage){
            OnlineHeartUDPMessage onlineHeartUDPMessage = new OnlineHeartUDPMessage();
            onlineHeartUDPMessage.setId(Short.MAX_VALUE);
            onlineHeartUDPMessage.setReceive(((OnlineHeartUDPMessage) netMessage).getSend());
            channelHandlerContext.writeAndFlush(onlineHeartUDPMessage).sync();
        }
    }

}
