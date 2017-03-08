package com.wolf.shoot.service.net;

import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.service.net.message.decoder.RpcDecoder;
import com.wolf.shoot.service.net.message.encoder.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by jwp on 2017/3/7.
 */
public class GameNetRPCChannleInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

        ChannelPipeline channelPipLine = nioSocketChannel.pipeline();
        int maxLength = Integer.MAX_VALUE;
        channelPipLine.addLast("frame", new LengthFieldBasedFrameDecoder(maxLength,0,4,0,0));
        channelPipLine.addLast("encoder", new RpcEncoder(RpcResponse.class));
        channelPipLine.addLast("decoder2", new RpcDecoder(RpcRequest.class));
        int readerIdleTimeSeconds = 0;
        int writerIdleTimeSeconds = 0;
        int allIdleTimeSeconds = GlobalConstants.Net.SESSION_HEART_ALL_TIMEOUT;
        channelPipLine.addLast("idleStateHandler", new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));
        channelPipLine.addLast("logger", new LoggingHandler(LogLevel.DEBUG));
        channelPipLine.addLast("handler", new GameNetRPCServerHandler());
    }
}
