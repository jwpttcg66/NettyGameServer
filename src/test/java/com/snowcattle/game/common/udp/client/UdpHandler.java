package com.snowcattle.game.common.udp.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
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
        utilLogger.debug("收到服务器端数据" + string);
        Thread.sleep(1000);
//        channelHandlerContext.write(string);
        //回复客户端
        String response = "Hello, 客户端事件为" + System.currentTimeMillis();
        byte[] bytes = response.getBytes(CharsetUtil.UTF_8);
        DatagramPacket responsePacket = new DatagramPacket(Unpooled.copiedBuffer(bytes), datagramPacket.sender());
//        channelHandlerContext.writeAndFlush(responsePacket).sync();
        channelHandlerContext.channel().writeAndFlush(responsePacket).sync();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * Gets called if an user event was triggered.
     */
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
        System.out.println("d");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelUnregistered();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelRegistered();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelActive();
    }


}
