package com.wolf.shoot.service.net;

/**
 * Created by jiangwenping on 17/2/7.
 */

import com.wolf.shoot.net.message.decoder.NetMessageDecoder;
import com.wolf.shoot.net.message.encoder.NetMessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by jwp on 2017/1/26.
 *  * LengthFieldBasedFrameDecoder 使用
 *  head+messagelength+serial+body
 *
 *  参数maxFrameLength 为数据帧最大长度
 *  参数lengthFieldOffset为version长度表示 从第几个字段开始读取长度，表示同意为head的长度
 *  参数lengthFieldLength表示占用了多少个字节数 具体可查看LengthFieldBasedFrameDecoder的getUnadjustedFrameLength方法
 *  参数lengthAdjustment表示还需要拓展长度，具体表示为serial的长度
 *  参数initialBytesToStrip表示 传递给下个coder的时候跳过多少字节 如果从0开始为 head+messagelength+serial+body全部给下个coder
 */
public class GameNetProtoMessageServerChannleInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

        ChannelPipeline channelPipLine = nioSocketChannel.pipeline();
        int maxLength = Integer.MAX_VALUE;
//        int lengthAdjustment = 1+2+4;

        nioSocketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(maxLength, 2, 4, 0, 0));
        nioSocketChannel.pipeline().addLast(new NetMessageEncoder());
        nioSocketChannel.pipeline().addLast(new NetMessageDecoder());
//        channelPipLine.addLast(new NetMessageSocketServerHandler());
    }
}
