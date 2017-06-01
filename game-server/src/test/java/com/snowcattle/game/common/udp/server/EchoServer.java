package com.snowcattle.game.common.udp.server;


import com.snowcattle.game.TestStartUp;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by jwp on 2017/1/20.
 */
public class EchoServer {
    public static void main(String[] args) throws Exception {
//        LocalSpringServiceManager localSpringServiceManager = new LocalSpringServiceManager();
//        MessageCommandFactory messageCommandFactory = new MessageCommandFactory();
//        localSpringBeanManager.setMessageCommandFactory(messageCommandFactory);
//        LocalMananger.getInstance().create(MessageRegistry.class, MessageRegistry.class);
//        localSpringServiceManager.setMessageRegistry(LocalMananger.getInstance().get(MessageRegistry.class));
//        LocalMananger.getInstance().setLocalSpringServiceManager(localSpringServiceManager);

        TestStartUp.startUp();
        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, false)
//                .option(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION,true)
                .option(ChannelOption.SO_REUSEADDR, true) //重用地址
                .option(ChannelOption.SO_RCVBUF, 65536)
                .option(ChannelOption.SO_SNDBUF, 65536)
                .option(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(false))  // heap buf 's better
                .handler(new LoggingHandler(LogLevel.DEBUG))
//                .handler(new UdpChannelInitializer());
                .handler(new UdpProtoBufServerChannelInitializer());

        // 服务端监听在9999端口
        b.bind(9999).sync().channel().closeFuture().sync();
    }
}
