package com.wolf.shoot.udp.server;


import io.netty.bootstrap.Bootstrap;
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
        Bootstrap b = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        b.group(group)
                .channel(NioDatagramChannel.class)
//                .handler(new ChannelInitializer<NioDatagramChannel>() {
//
//                    @Override
//                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                        super.channelActive(ctx);
//                    }
//
//                    @Override
//                    protected void initChannel(NioDatagramChannel ch) throws Exception {
//                        ChannelPipeline cp = ch.pipeline();
//                        cp.addLast("framer", new MessageToMessageDecoder<DatagramPacket>() {
//                            @Override
//                            protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
//                                String string = msg.content().toString(Charset.forName("UTF-8"));
//                                out.add(msg.content().toString(Charset.forName("UTF-8")));
//                                System.out.println(string);
//                            }
//                        }).addLast("handler", new EchoServerHandler());
//                    }
//                }); .option(ChannelOption.TCP_NODELAY, true)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                    .handler( new EchoServerHandler());

        // 服务端监听在9999端口
        b.bind(9999).sync().channel().closeFuture().await();
    }
}
