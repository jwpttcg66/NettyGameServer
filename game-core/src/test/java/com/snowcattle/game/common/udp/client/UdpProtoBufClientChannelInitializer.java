package com.snowcattle.game.common.udp.client;

/**
 * Created by jiangwenping on 17/2/16.
 */

import com.snowcattle.game.service.message.decoder.NetProtoBufMessageUDPDecoder;
import com.snowcattle.game.service.message.encoder.NetProtoBufMessageUDPEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by jiangwenping on 17/2/16.
 */
public class UdpProtoBufClientChannelInitializer extends ChannelInitializer<NioDatagramChannel> {
    @Override
    protected void initChannel(NioDatagramChannel ch) throws Exception {
        ChannelPipeline channelPipLine = ch.pipeline();
        int maxLength = Integer.MAX_VALUE;
        int lengthAdjustment = 1+2+4;

        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxLength, 2, 4, 0, 0));
        ch.pipeline().addLast(new NetProtoBufMessageUDPEncoder());
        ch.pipeline().addLast(new NetProtoBufMessageUDPDecoder());
        channelPipLine.addLast(new UdpProtoBufHandler());
    }
}
