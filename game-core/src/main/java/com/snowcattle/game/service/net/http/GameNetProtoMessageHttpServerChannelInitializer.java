package com.snowcattle.game.service.net.http;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.message.decoder.NetProtoBufMessageTCPDecoder;
import com.snowcattle.game.service.message.encoder.NetProtoBufMessageTCPEncoder;
import com.snowcattle.game.service.net.tcp.handler.GameLoggingHandler;
import com.snowcattle.game.service.net.tcp.handler.GameNetMessageTcpServerHandler;
import com.snowcattle.game.service.net.tcp.handler.async.AsyncNettyGameNetMessageTcpServerHandler;
import com.snowcattle.game.service.net.tcp.handler.async.AsyncNettyTcpHandlerService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.IdleStateHandler;

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
//        AsyncNettyTcpHandlerService asyncNettyTcpHandlerService = LocalMananger.getInstance().getLocalSpringServiceManager().getAsyncNettyTcpHandlerService();
//        channelPipLine.addLast(asyncNettyTcpHandlerService.getDefaultEventExecutorGroup(), new AsyncNettyGameNetMessageTcpServerHandler());
    }
}