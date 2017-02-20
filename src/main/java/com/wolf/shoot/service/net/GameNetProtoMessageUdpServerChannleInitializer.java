package com.wolf.shoot.service.net;

import com.wolf.shoot.service.net.message.decoder.NetProtoBufMessageUDPDecoder;
import com.wolf.shoot.service.net.message.encoder.NetProtoBufMessageUDPEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by jwp on 2017/2/17.
 */
public class GameNetProtoMessageUdpServerChannleInitializer  extends ChannelInitializer<NioDatagramChannel> {

    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ChannelPipeline channelPipLine = ch.pipeline();
        int maxLength = Integer.MAX_VALUE;
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxLength, 2, 4, 0, 0));
        ch.pipeline().addLast(new NetProtoBufMessageUDPEncoder());
        ch.pipeline().addLast(new NetProtoBufMessageUDPDecoder());
        channelPipLine.addLast("logger", new LoggingHandler(LogLevel.DEBUG));
        channelPipLine.addLast(new GameNetMessageUdpServerHandler());
    }
}
