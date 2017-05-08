package com.snowcattle.game.net.client.tcp;

import com.snowcattle.game.message.logic.tcp.online.client.OnlineLoginClientTcpMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by jiangwenping on 17/2/8.
 */

public class GameClientHandler  extends ChannelInboundHandlerAdapter {
//    private final ByteBuf firstMessage;

    public GameClientHandler() {
//        firstMessage = Unpooled.buffer(1024);
//        byte[] sendString = "hello world".getBytes();
//        firstMessage.writeInt(sendString.length);
//        firstMessage.writeBytes(sendString);
//        firstMessage.writeInt(234);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        OnlineHeartClientTcpMessage onlineHeartClientTcpMessage = new OnlineHeartClientTcpMessage();
//        onlineHeartClientTcpMessage.setId(Integer.MAX_VALUE);
//        ctx.writeAndFlush(onlineHeartClientTcpMessage);

        OnlineLoginClientTcpMessage onlineLoginClientTcpMessage = new OnlineLoginClientTcpMessage();
        onlineLoginClientTcpMessage.setId(Integer.MAX_VALUE);
        ctx.writeAndFlush(onlineLoginClientTcpMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ctx.write(msg);
        System.out.println(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}

