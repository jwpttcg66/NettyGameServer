package com.snowcattle.game.service.rpc.client.net;

import com.snowcattle.game.service.net.tcp.RpcRequest;
import com.snowcattle.game.service.net.tcp.RpcResponse;
import com.snowcattle.game.service.message.decoder.RpcDecoder;
import com.snowcattle.game.service.message.encoder.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 */
public class RpcClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        int maxLength = Integer.MAX_VALUE;
        channelPipeline.addLast(new LengthFieldBasedFrameDecoder(maxLength, 0, 4, 0, 0));
        channelPipeline.addLast(new RpcEncoder(RpcRequest.class));
        channelPipeline.addLast(new RpcDecoder(RpcResponse.class));
        channelPipeline.addLast("logger", new LoggingHandler(LogLevel.DEBUG));
        channelPipeline.addLast(new RpcClientHandler());
    }
}
