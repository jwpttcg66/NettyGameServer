package com.wolf.shoot.service.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * Created by jiangwenping on 17/2/7.
 */
public abstract class AbstractNettyTcpServerService extends AbstractTcpServerService {

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public AbstractNettyTcpServerService(String serviceId, int serverPort, InetSocketAddress serverAddress) {
        super(serviceId);
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
    }

    @Override
    public boolean startService() {
        boolean serviceFlag  = super.startService();
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap = serverBootstrap.group(bossGroup, bossGroup);
            serverBootstrap.channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new GameNetProtoMessageServerChannleInitializer());
            ChannelFuture serverChannelFuture = serverBootstrap.bind(serverPort).sync();

            serverChannelFuture.channel().closeFuture().sync();
        }catch (Exception e) {
            serviceFlag = false;

        }
        return serviceFlag;
    }

    @Override
    public boolean stopService(){
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
