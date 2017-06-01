package com.snowcattle.game.common.socket.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by jiangwenping on 17/1/22.
 */
public class EchoSocketServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        String body=(String)msg;
//        System.out.println("服务端收到："+body+"，次数:"+ ++counter);
//        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time=dateFormat.format(new Date());
//        String res="来自与服务端的回应,时间:"+ time;
//        ByteBuf resp= Unpooled.copiedBuffer(res.getBytes());
//        ctx.writeAndFlush(resp);
////        ctx.writeAndFlush(res);
//        counter++;
//        ByteBuf buf=(ByteBuf)msg;
//        byte[] req=new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body=new String(req, CharsetUtil.UTF_8);
//        System.out.println("body:" + body + ",响应次数:" + (++counter));
//        ctx.writeAndFlush(buf);
        Thread.sleep(1000L);
        ctx.writeAndFlush(msg);
        System.out.println("服务端收到："+msg);
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



    private int counter;

}
