package com.wolf.shoot.udp.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by jwp on 2017/1/20.
 */
public class EchoServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    public static final Logger utilLogger = LoggerFactory.getLogger("util");

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        //读取数据
//        ByteBuffer buf =  ByteBuffer.wrap(datagramPacket.copy().content().array());
//        ByteBuffer readBuffer = buf.asReadOnlyBuffer();
//        String body = new String(readBuffer.array(), CharsetUtil.UTF_8);
        String string = datagramPacket.content().toString(Charset.forName("UTF-8"));
        utilLogger.debug("收到客户端数据" + string);

        //回复客户端
        String response = "Hello, 服务器事件为" + System.currentTimeMillis();
        byte[] bytes = response.getBytes(CharsetUtil.UTF_8);
        DatagramPacket responsePacket = new DatagramPacket(Unpooled.copiedBuffer(bytes), datagramPacket.sender());
//        channelHandlerContext.writeAndFlush(responsePacket).sync();
        channelHandlerContext.channel().writeAndFlush(responsePacket).sync();
    }
}
