package com.snowcattle.game.common.codec.client;

import com.snowcattle.game.common.socket.client.LengthStringClientChannleInitializer;
import com.snowcattle.game.common.socket.client.NetMessageClientChannelInitializer;
import com.snowcattle.game.common.socket.client.StringClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by jiangwenping on 2017/11/14.
 */

public class CodeCClient {


    public static void main(String[] args) throws Exception {
        new CodeCClient().connect("127.0.0.1", 9999);
//        new EchoClient().connect("127.0.0.1", 7090);
    }

    public void connect(String addr, int port) throws Exception {
        final EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new CodeCStringClientChannelInitializer());
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
