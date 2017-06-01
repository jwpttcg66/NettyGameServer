package com.snowcattle.game.common.socket.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by jwp on 2017/1/23.
 */
public class ClientChannleInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        nioSocketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        nioSocketChannel.pipeline().addLast(new StringDecoder());
        nioSocketChannel.pipeline().addLast(new StringEncoder());
        nioSocketChannel.pipeline().addLast(new EchoClientHandler());
    }
}
