package com.wolf.shoot.socket.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by jiangwenping on 17/1/24.
 */
public class ProtoClientChannleInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//        nioSocketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
//        nioSocketChannel.pipeline().addLast(new StringDecoder());
//        nioSocketChannel.pipeline().addLast(new StringEncoder());
        nioSocketChannel.pipeline().addLast(new ProtoClientHandler());
    }
}
