package com.wolf.shoot.socket.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by jiangwenping on 17/1/24.
 */
public class ProtoServerChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        ChannelPipeline channelPipLine = nioSocketChannel.pipeline();
//        channelPipLine.addLast(new LineBasedFrameDecoder(1024));
//        channelPipLine.addLast(new StringDecoder());
//        channelPipLine.addLast(new StringEncoder());
        channelPipLine.addLast(new ProtoSocketServerHandler());
    }
}
