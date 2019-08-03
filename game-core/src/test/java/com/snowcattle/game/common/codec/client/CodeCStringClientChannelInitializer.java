package com.snowcattle.game.common.codec.client;

import com.snowcattle.game.common.socket.client.EchoStringSocketClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by jiangwenping on 2017/11/14.
 */
public class CodeCStringClientChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        nioSocketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
        nioSocketChannel.pipeline().addLast(new StringDecoder());
        nioSocketChannel.pipeline().addLast(new StringEncoder());
        nioSocketChannel.pipeline().addLast(new EchoStringSocketClientHandler());
    }
}
