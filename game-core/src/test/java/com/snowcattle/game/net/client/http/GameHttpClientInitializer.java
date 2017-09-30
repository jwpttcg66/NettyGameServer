package com.snowcattle.game.net.client.http;

import com.snowcattle.game.common.http.HttpSnoopClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;

/**
 * Created by jiangwenping on 2017/9/29.
 */
public class GameHttpClientInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public GameHttpClientInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();

        // Enable HTTPS if necessary.
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));
        }

//        p.addLast(new HttpClientCodec());
//
//        // Remove the following line if you don't want automatic content decompression.
//        p.addLast(new HttpContentDecompressor());

        // Uncomment the following line if you don't want to handle HttpContents.
        //p.addLast(new HttpObjectAggregator(1048576));

        p.addLast("encoder", new HttpRequestEncoder());
//        p.addLast("trunk", new HttpObjectAggregator(1048576));
        p.addLast("decoder", new HttpResponseDecoder());

        p.addLast(new GameHttpClientHandler());
    }
}

