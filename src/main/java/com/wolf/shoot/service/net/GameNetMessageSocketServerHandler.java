package com.wolf.shoot.service.net;

import com.wolf.shoot.net.message.NetMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * Created by jiangwenping on 17/2/7.
 */
public class GameNetMessageSocketServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Thread.sleep(1000L);
//        ByteBuf byteBuffer = (ByteBuf) msg;

//        System.out.println("服务端收到："+byteBuffer.array());
//        ctx.writeAndFlush(msg);
        NetMessage netMessage = (NetMessage) msg;
        String requst = new String(netMessage.getNetMessageBody().getBytes(), CharsetUtil.UTF_8);
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
    public void userEventTriggered(ChannelHandlerContext ctx, Object var) throws Exception{
        super.userEventTriggered(ctx, var);
        if(var instanceof IdleStateEvent){
            //说明是空闲事件
            ctx.close();
        }
    }

}
