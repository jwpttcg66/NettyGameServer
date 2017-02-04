package com.wolf.shoot.socket.client;

import com.wolf.shoot.net.message.decoder.NetMessageDecoder;
import com.wolf.shoot.net.message.decoder.NetMessageDecoderFactory;
import com.wolf.shoot.net.message.encoder.NetMessageEncoder;
import com.wolf.shoot.net.message.encoder.NetMessageEncoderFactory;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by jwp on 2017/1/26.
 */
public class NetMessageClientChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        int maxLength = Integer.MAX_VALUE;
//        nioSocketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxLength, 0, 4, 0, 0));
        int lengthAdjustment = 1+2+4;

        nioSocketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxLength, 2, 4, 0, 0));
        nioSocketChannel.pipeline().addLast(new NetMessageEncoder());
        nioSocketChannel.pipeline().addLast(new NetMessageDecoder());
        nioSocketChannel.pipeline().addLast(new NetMessageClientHandler());
    }
}
