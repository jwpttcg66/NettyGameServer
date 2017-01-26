package com.wolf.shoot.socket.server;

import com.wolf.shoot.net.message.decoder.NetMessageDecoder;
import com.wolf.shoot.net.message.encoder.NetMessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by jwp on 2017/1/26.
 */
public class NetMessageServerChannleInitializer  extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

        ChannelPipeline channelPipLine = nioSocketChannel.pipeline();
        int maxLength = Integer.MAX_VALUE;
        nioSocketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxLength, 0, 4, 0, 0));
        nioSocketChannel.pipeline().addLast(new NetMessageEncoder());
        nioSocketChannel.pipeline().addLast(new NetMessageDecoder());
        channelPipLine.addLast(new ProtoSocketServerHandler());
    }
}
