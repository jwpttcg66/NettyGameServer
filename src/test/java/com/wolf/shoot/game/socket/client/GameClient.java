package com.wolf.shoot.game.socket.client;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.manager.spring.LocalSpringServiceManager;
import com.wolf.shoot.service.net.message.registry.MessageRegistry;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by jiangwenping on 17/2/8.
 */

public class GameClient {

    public static void main(String[] args) throws Exception {
        LocalSpringServiceManager localSpringServiceManager = new LocalSpringServiceManager();
        GameServerConfigService gameServerConfigService = new GameServerConfigService();
        gameServerConfigService.startup();;
        localSpringServiceManager.setGameServerConfigService(gameServerConfigService);
        LocalMananger.getInstance().setLocalSpringServiceManager(localSpringServiceManager);
        LocalMananger.getInstance().create(MessageRegistry.class, MessageRegistry.class);
        localSpringServiceManager.setMessageRegistry(LocalMananger.getInstance().get(MessageRegistry.class));

        new GameClient().connect("127.0.0.1", 7090);
    }

    public void connect(String addr, int port) throws Exception {
        final EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .handler(new GameClientChannleInitializer());
            ChannelFuture f = b.connect(addr, port).sync();
            System.out.println("连接服务器:" + f.channel().remoteAddress() + ",本地地址:" + f.channel().localAddress());
            f.channel().closeFuture().sync();//等待客户端关闭连接

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            group.shutdownGracefully();
        }
    }
}

