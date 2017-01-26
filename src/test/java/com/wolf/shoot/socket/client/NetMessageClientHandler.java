package com.wolf.shoot.socket.client;

import com.wolf.shoot.net.message.NetMessage;
import com.wolf.shoot.net.message.NetMessageBody;
import com.wolf.shoot.net.message.NetMessageHead;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by jwp on 2017/1/26.
 */
public class NetMessageClientHandler extends ChannelInboundHandlerAdapter {
    private final NetMessage netMessage;

    public NetMessageClientHandler() {
        netMessage = new NetMessage();
        NetMessageHead netMessageHead = new NetMessageHead();
        netMessageHead.setSerial(5);
        netMessageHead.setCmd((short) 2);
        netMessageHead.setVersion((byte) 3);

        NetMessageBody netMessageBody = new NetMessageBody();
        byte[] bytes = "hello world".getBytes(CharsetUtil.UTF_8);
        netMessageBody.setBytes(bytes);

        netMessage.setNetMessageBody(netMessageBody);
        netMessage.setNetMessageHead(netMessageHead);

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(netMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.write(msg);
        System.out.println(msg);
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
}
