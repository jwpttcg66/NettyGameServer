package com.snowcattle.game.common.socket.client;

import com.snowcattle.game.service.message.decoder.NetMessageTCPDecoder;
import com.snowcattle.game.service.message.encoder.NetMessageTcpEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

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
        nioSocketChannel.pipeline().addLast(new NetMessageTcpEncoder());
        nioSocketChannel.pipeline().addLast(new NetMessageTCPDecoder());
        nioSocketChannel.pipeline().addLast(new NetMessageClientHandler());
    }
}
