package com.wolf.shoot.service.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * Created by jwp on 2017/2/4.
 */
public abstract class AbstractNettyUdpServerService extends  AbstractNettyServerService{

    private EventLoopGroup eventLoopGroup;

    private ThreadNameFactory eventThreadNameFactory;

    public AbstractNettyUdpServerService(String serviceId, int serverPort, InetSocketAddress serverAddress, ThreadNameFactory threadNameFactory) {
        super(serviceId, serverPort);
        this.eventThreadNameFactory = eventThreadNameFactory;
    }

    @Override
    public boolean startService() throws Exception{
        boolean serviceFlag  = super.startService();
        Bootstrap b = new Bootstrap();
        eventLoopGroup = new NioEventLoopGroup();
        b.group(eventLoopGroup)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, false)
//                .option(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION,true)
                .option(ChannelOption.SO_REUSEADDR, true) //重用地址
                .option(ChannelOption.SO_RCVBUF, 65536)
                .option(ChannelOption.SO_SNDBUF, 65536)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false))  // heap buf 's better
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new GameNetProtoMessageUdpServerChannleInitializer());

        // 服务端监听在9999端口
        b.bind(serverPort).sync().channel().closeFuture().await();
        return serviceFlag;
    }

    @Override
    public boolean stopService() throws Exception{
        boolean flag = super.stopService();
        if(eventLoopGroup != null){
            eventLoopGroup.shutdownGracefully();
        }
        return flag;
    }

}
