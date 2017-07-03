package com.snowcattle.game.net.client.udp;

import com.snowcattle.game.TestStartUp;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.bootstrap.manager.spring.LocalSpringServiceManager;
import com.snowcattle.game.message.logic.udp.online.OnlineHeartClientUDPMessage;
import com.snowcattle.game.common.udp.client.UdpProtoBufClientChannelInitializer;
import com.snowcattle.game.service.message.registry.MessageRegistry;
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
        TestStartUp.startUp();
        LocalSpringServiceManager localSpringServiceManager = LocalMananger.getInstance().getLocalSpringServiceManager();
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


