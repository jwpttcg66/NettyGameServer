package com.snowcattle.game.service.net.http;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.net.http.handler.HttpServerHandler;
import com.snowcattle.game.service.net.http.handler.async.AsyncNettyHttpHandlerService;
import com.snowcattle.game.service.net.tcp.handler.GameLoggingHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;

/**
 * Created by jiangwenping on 2017/7/3.
 */

public class GameNetProtoMessageHttpServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline channelPipLine = socketChannel.pipeline();
        int maxLength = Integer.MAX_VALUE;
//        channelPipLine.addLast("frame", new LengthFieldBasedFrameDecoder(maxLength, 2, 4, 0, 0));
        channelPipLine.addLast("encoder", new HttpResponseEncoder());
        channelPipLine.addLast("trunk", new HttpObjectAggregator(1048576));
        channelPipLine.addLast("decoder", new HttpRequestDecoder());
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        if(gameServerConfig.isDevelopModel()) {
            channelPipLine.addLast("logger", new GameLoggingHandler(LogLevel.DEBUG));
        }
        AsyncNettyHttpHandlerService asyncNettyHttpHandlerService = LocalMananger.getInstance().getLocalSpringServiceManager().getAsyncNettyHttpHandlerService();
        channelPipLine.addLast(asyncNettyHttpHandlerService.getDefaultEventExecutorGroup(), new HttpServerHandler());
    }
}