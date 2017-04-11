package com.wolf.shoot.game.udp.client;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.manager.spring.LocalSpringServiceManager;
import com.wolf.shoot.message.logic.udp.online.OnlineHeartClientUDPMessage;
import com.wolf.shoot.service.net.message.registry.MessageRegistry;
import com.wolf.shoot.common.udp.client.UdpProtoBufClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Created by jwp on 2017/2/17.
 */
public class GameNettyUdpClient {
    public static int port = 10090;
    public static void main(String[] args) throws Exception {
        LocalSpringServiceManager localSpringServiceManager = new LocalSpringServiceManager();
        GameServerConfigService gameServerConfigService = new GameServerConfigService();
        gameServerConfigService.startup();;
        localSpringServiceManager.setGameServerConfigService(gameServerConfigService);
        LocalMananger.getInstance().setLocalSpringServiceManager(localSpringServiceManager);
        LocalMananger.getInstance().create(MessageRegistry.class, MessageRegistry.class);
        localSpringServiceManager.setMessageRegistry(LocalMananger.getInstance().get(MessageRegistry.class));


        final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioDatagramChannel.class);
        bootstrap.group(nioEventLoopGroup);
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
//        bootstrap.handler(new UdpClientChannelInitializer());
        bootstrap.handler(new UdpProtoBufClientChannelInitializer());
        // 监听端口
        ChannelFuture sync = bootstrap.bind(0).sync();
        Channel udpChannel = sync.channel();

//        sendStringMessage(udpChannel);
        sendMessage(udpChannel);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                nioEventLoopGroup.shutdownGracefully();
            }
        }));

        while (true){
            Thread.currentThread().sleep(100l);
        }
    }

    public static void sendStringMessage(Channel udpChannel) throws InterruptedException {
        String data = "我是大好人啊";
        udpChannel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(data.getBytes(Charset.forName("UTF-8"))), new InetSocketAddress("127.0.0.1", port))).sync();
    }

    public static void sendMessage(Channel udpChannel) throws InterruptedException {
        OnlineHeartClientUDPMessage onlineHeartClientUdpMessage = new OnlineHeartClientUDPMessage();
        onlineHeartClientUdpMessage.setId(Short.MAX_VALUE);
        long playerId = 6666;
        int tocken = 333;
        onlineHeartClientUdpMessage.setPlayerId(playerId);
        onlineHeartClientUdpMessage.setTocken(tocken);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", port);
        onlineHeartClientUdpMessage.setReceive(inetSocketAddress);
        udpChannel.writeAndFlush(onlineHeartClientUdpMessage).sync();
    }
}


