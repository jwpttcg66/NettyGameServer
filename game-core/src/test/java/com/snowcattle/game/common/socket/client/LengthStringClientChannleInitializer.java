package com.snowcattle.game.common.socket.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by jiangwenping on 17/1/24.
 */
public class LengthStringClientChannleInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        short maxLength = Short.MAX_VALUE;
        nioSocketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxLength, 0, 4));
        nioSocketChannel.pipeline().addLast(new StringDecoder());
//        nioSocketChannel.pipeline().addLast(new StringEncoder());
        nioSocketChannel.pipeline().addLast(new LenghtStringClientHandler());
    }
}
