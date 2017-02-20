package com.wolf.shoot.udp.client;

import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.message.logic.udp.online.OnlineHeartUDP2Message;
import com.wolf.shoot.service.net.message.registry.MessageRegistry;
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
 * @author TinyZ on 2015/6/8.
 */
public class EchoNettyUdpClient {

    public static void main(String[] args) throws Exception {
        LocalMananger.getInstance().create(MessageRegistry.class, MessageRegistry.class);
        final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioDatagramChannel.class);
        bootstrap.group(nioEventLoopGroup);
        bootstrap.handler(new LoggingHandler(LogLevel.INFO));
//        bootstrap.handler(new UdpClientChannelInitializer());
        bootstrap.handler(new UdpProtoBufClientChannelInitializer());
        // 监听端口
        int port = 9999;
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
        int port = 9999;
        String data = "我是大好人啊";
        udpChannel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(data.getBytes(Charset.forName("UTF-8"))), new InetSocketAddress("127.0.0.1", port))).sync();
    }

    public static void sendMessage(Channel udpChannel) throws InterruptedException {
        int port = 9999;
        OnlineHeartUDP2Message onlineHeartUDPMessage = new OnlineHeartUDP2Message();
        onlineHeartUDPMessage.setId(Short.MAX_VALUE);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", port);
        onlineHeartUDPMessage.setReceive(inetSocketAddress);
        udpChannel.writeAndFlush(onlineHeartUDPMessage).sync();
    }
}