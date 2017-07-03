package com.snowcattle.game.service.net.http;

import com.snowcattle.game.common.ThreadNameFactory;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.service.net.AbstractNettyServerService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;

/**
 * Created by jiangwenping on 2017/7/3.
 */
public abstract class AbstractNettyHttpServerService extends AbstractNettyServerService {

    private Logger logger = Loggers.serverLogger;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private ThreadNameFactory bossThreadNameFactory;
    private ThreadNameFactory workerThreadNameFactory;
    private ChannelInitializer channelInitializer;

    private ChannelFuture serverChannelFuture;

    public AbstractNettyHttpServerService(String serviceId, int serverPort, String bossTreadName, String workThreadName,ChannelInitializer channelInitializer) {
        super(serviceId, serverPort);
        this.bossThreadNameFactory = new ThreadNameFactory(bossTreadName);
        this.workerThreadNameFactory = new ThreadNameFactory(workThreadName);
        this.channelInitializer = channelInitializer;
    }

    @Override
    public boolean startService() throws Exception{
        boolean serviceFlag  = super.startService();
        bossGroup = new NioEventLoopGroup(1, bossThreadNameFactory);
        workerGroup = new NioEventLoopGroup(0, workerThreadNameFactory);
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap = serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_REUSEADDR, true) //重用地址
                    .childOption(ChannelOption.SO_RCVBUF, 65536)
                    .childOption(ChannelOption.SO_SNDBUF, 65536)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false))  // heap buf 's better
                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, Integer.valueOf(1000))
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(channelInitializer);


//            bootstrap.setOption("child.tcpNoDelay", Boolean.valueOf(true));
//            bootstrap.setOption("child.keepAlive", Boolean.valueOf(true));
//            bootstrap.setOption("child.reuseAddress", Boolean.valueOf(true));
//            bootstrap.setOption("child.connectTimeoutMillis", Integer.valueOf(100));
            serverChannelFuture = serverBootstrap.bind(serverPort).sync();

            serverChannelFuture.channel().closeFuture().addListener(ChannelFutureListener.CLOSE);
        }catch (Exception e) {
            logger.error(e.toString(), e);
            serviceFlag = false;
        }
        return serviceFlag;
    }

    @Override
    public boolean stopService() throws Exception{
        boolean flag = super.stopService();
        if(bossGroup != null){
            bossGroup.shutdownGracefully();
        }
        if(workerGroup != null){
            workerGroup.shutdownGracefully();
        }
        return flag;
    }

}
