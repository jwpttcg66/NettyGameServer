package com.snowcattle.game.common.udp.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Created by jiangwenping on 17/2/16.
 */
public class UdpClientChannelInitializer extends ChannelInitializer<NioDatagramChannel> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ChannelPipeline cp = ch.pipeline();
        cp.addLast("handler", new UdpHandler());
    }
}

