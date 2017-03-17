package com.wolf.shoot.service.net;

import com.wolf.shoot.common.ThreadNameFactory;
import com.wolf.shoot.common.constant.Loggers;
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
import org.slf4j.Logger;

/**
 * Created by jiangwenping on 17/2/7.
 */
public abstract class AbstractNettyTcpServerService extends AbstractNettyServerService {

    private Logger logger = Loggers.serverLogger;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private ThreadNameFactory bossThreadNameFactory;
    private ThreadNameFactory workerThreadNameFactory;
    private ChannelInitializer channelInitializer;

    private ChannelFuture serverChannelFuture;
    public AbstractNettyTcpServerService(String serviceId, int serverPort, String bossTreadName, String workThreadName,ChannelInitializer channelInitializer) {
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
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(channelInitializer);

            serverChannelFuture = serverBootstrap.bind(serverPort).sync();

            //TODO这里会阻塞main线程，暂时先注释掉
//            serverChannelFuture.channel().closeFuture().sync();
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

    public void finish() throws InterruptedException {
        serverChannelFuture.channel().closeFuture().sync();
    }
}
