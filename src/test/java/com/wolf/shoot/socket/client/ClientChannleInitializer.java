package com.wolf.shoot.socket.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by jwp on 2017/1/23.
 */
public class ClientChannleInitializer extends ChannelInitializer<NioSocketChannel>{
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        //                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
//                            ch.pipeline().addLast(new StringDecoder());
//                            ch.pipeline().addLast(new StringEncoder());
        nioSocketChannel.pipeline().addLast(new EchoClientHandler());
//                            ch.pipeline().addLast(new EchoClientHandler2());
    }
}
