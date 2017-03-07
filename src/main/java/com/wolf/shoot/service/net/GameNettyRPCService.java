package com.wolf.shoot.service.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by jwp on 2017/3/7.
 * 增加rpc服务
 */
public class GameNettyRPCService extends AbstractNettyTcpServerService{

    public GameNettyRPCService(String serviceId, int serverPort, String bossTreadName, String workThreadName, ChannelInitializer channelInitializer) {
        super(serviceId, serverPort, bossTreadName, workThreadName, channelInitializer);
    }
}
