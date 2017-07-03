package com.snowcattle.game.common.socket.server;

import com.snowcattle.game.service.message.AbstractNetMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by jwp on 2017/1/26.
 */
public class NetMessageSocketServerHandler  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Thread.sleep(1000L);
//        ByteBuf byteBuffer = (ByteBuf) msg;

//        System.out.println("服务端收到："+byteBuffer.array());
        ctx.writeAndFlush(msg);
        AbstractNetMessage abstractNetMessage = (AbstractNetMessage) msg;
        String requst = new String(abstractNetMessage.getNetMessageBody().getBytes(), CharsetUtil.UTF_8);
        System.out.println("服务端收到：" + requst);

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
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
    }
}
