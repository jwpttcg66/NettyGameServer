package com.wolf.shoot.udp.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;


/**
 * Created by jiangwenping on 17/1/22.
 */
public class UdpHandler  extends SimpleChannelInboundHandler<DatagramPacket> {

    public static final Logger utilLogger = LoggerFactory.getLogger("util");
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        String string = datagramPacket.content().toString(Charset.forName("UTF-8"));
        utilLogger.debug("收到客户端数据" + string);
    }
}
