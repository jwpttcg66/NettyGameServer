package com.snowcattle.game.common.udp.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Created by jiangwenping on 17/2/16.
 */
public class UdpChannelInitializer  extends ChannelInitializer<NioDatagramChannel> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ChannelPipeline cp = ch.pipeline();
//        cp.addLast("framer", new MessageToMessageDecoder<DatagramPacket>() {
//            @Override
//            protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
//                String string = msg.content().toString(Charset.forName("UTF-8"));
//                out.add(msg.content().toString(Charset.forName("UTF-8")));
//                System.out.println(string);
//            }
//        });
        cp.addLast("handler", new EchoServerHandler());
    }
}
