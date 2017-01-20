package com.wolf.shoot.udp.server;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Created by jwp on 2017/1/20.
 */
public class EchoServer {
    public static void main(String[] args)  throws Exception{
        Bootstrap serverBootStrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        serverBootStrap.group(group).channel(NioDatagramChannel.class).handler(new EchoServerHandler());
        //绑定端口
        int port = 9090;
        serverBootStrap.bind().sync().channel().closeFuture().await();

    }
}
