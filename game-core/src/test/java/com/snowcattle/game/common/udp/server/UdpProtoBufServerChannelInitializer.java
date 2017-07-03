package com.snowcattle.game.common.udp.server;

import com.snowcattle.game.service.message.decoder.NetProtoBufMessageUDPDecoder;
import com.snowcattle.game.service.message.encoder.NetProtoBufMessageUDPEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by jiangwenping on 17/2/16.
 */
public class UdpProtoBufServerChannelInitializer extends ChannelInitializer<NioDatagramChannel> {
    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ChannelPipeline channelPipLine = ch.pipeline();
        int maxLength = Integer.MAX_VALUE;
//        nioSocketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxLength, 0, 4, 0, 0));
        int lengthAdjustment = 1+2+4;

        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxLength, 2, 4, 0, 0));
        ch.pipeline().addLast(new NetProtoBufMessageUDPEncoder());
        ch.pipeline().addLast(new NetProtoBufMessageUDPDecoder());
        channelPipLine.addLast("logger", new LoggingHandler(LogLevel.DEBUG));
        channelPipLine.addLast(new EchoServerHandler());
    }
}
