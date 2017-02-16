package com.wolf.shoot.udp.server;

import com.wolf.shoot.net.message.decoder.NetMessageTCPDecoder;
import com.wolf.shoot.net.message.encoder.NetMessageTcpEncoder;
import com.wolf.shoot.socket.server.NetMessageSocketServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

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
        ch.pipeline().addLast(new NetMessageTcpEncoder());
        ch.pipeline().addLast(new NetMessageTCPDecoder());
        channelPipLine.addLast(new NetMessageSocketServerHandler());
    }
}
