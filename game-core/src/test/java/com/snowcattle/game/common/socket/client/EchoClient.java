package com.snowcattle.game.common.socket.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * netty 客户端模拟
 *
 */
public class EchoClient {


    public static void main(String[] args) throws Exception {
        new EchoClient().connect("127.0.0.1", 9999);
//        new EchoClient().connect("127.0.0.1", 7090);
    }

    public void connect(String addr, int port) throws Exception {
        final EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
//                    .handler(new ClientChannleInitializer());
//                    .handler(new StringClientChannelInitializer());
//                    .handler(new LengthStringClientChannleInitializer());
                    .handler(new NetMessageClientChannelInitializer());
            ChannelFuture f = b.connect(addr, port).sync();
            System.out.println("连接服务器:" + f.channel().remoteAddress() + ",本地地址:" + f.channel().localAddress());
            f.channel().closeFuture().sync();//等待客户端关闭连接
//            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    group.shutdownGracefully();
//                }
//            }));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            group.shutdownGracefully();
        }
    }
}
