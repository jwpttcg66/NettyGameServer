package com.snowcattle.game.net.client.http;

import com.snowcattle.game.TestStartUp;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.bootstrap.manager.spring.LocalSpringServiceManager;
import com.snowcattle.game.common.http.HttpSnoopClientInitializer;
import com.snowcattle.game.message.logic.http.client.OnlineHeartClientHttpMessage;
import com.snowcattle.game.service.message.encoder.NetProtoBufHttpMessageEncoderFactory;
import com.snowcattle.game.service.message.registry.MessageRegistry;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.DefaultCookie;
import io.netty.handler.codec.http.cookie.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.net.URI;

/**
 * Created by jiangwenping on 2017/9/29.
 */
public final class GameHttpClient {
    //    static final String URL = System.getProperty("url", "http://127.0.0.1:8080/");
    static final String URL = System.getProperty("url", "http://127.0.0.1:10200/");
    public static void main(String[] args) throws Exception {


        TestStartUp.startUpWithSpring();
        LocalSpringServiceManager localSpringServiceManager = LocalMananger.getInstance().getLocalSpringServiceManager();
        localSpringServiceManager.setMessageRegistry(LocalMananger.getInstance().get(MessageRegistry.class));

        URI uri = new URI(URL);
        String scheme = uri.getScheme() == null? "http" : uri.getScheme();
        String host = uri.getHost() == null? "127.0.0.1" : uri.getHost();
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }

        if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
            System.err.println("Only HTTP(S) is supported.");
            return;
        }

        // Configure SSL context if necessary.
        final boolean ssl = "https".equalsIgnoreCase(scheme);
        final SslContext sslCtx;
        if (ssl) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new GameHttpClientInitializer(sslCtx));

            // Make the connection attempt.
            Channel ch = b.connect(host, port).sync().channel();


            //写入消息
            OnlineHeartClientHttpMessage onlineHeartClientHttpMessage = new OnlineHeartClientHttpMessage();
            onlineHeartClientHttpMessage.setId(1110);
            NetProtoBufHttpMessageEncoderFactory netProtoBufHttpMessageEncoderFactory  = new NetProtoBufHttpMessageEncoderFactory();
            ByteBuf byteBuf = netProtoBufHttpMessageEncoderFactory.createByteBuf(onlineHeartClientHttpMessage);

            // Prepare the HTTP request.
            HttpRequest request = new DefaultFullHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.POST, uri.getRawPath(), byteBuf);
            request.headers().set(HttpHeaderNames.HOST, host);
            request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);

            // Set some example cookies.
            request.headers().set(
                    HttpHeaderNames.COOKIE,
                    io.netty.handler.codec.http.cookie.ClientCookieEncoder.STRICT.encode(
                            new io.netty.handler.codec.http.cookie.DefaultCookie("my-cookie", "foo"),
                            new io.netty.handler.codec.http.cookie.DefaultCookie("another-cookie", "bar")));


            byte[] content = byteBuf.array();

            request.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.length);
            // Send the HTTP request.
            ch.writeAndFlush(request);

            // Wait for the server to close the connection.
            ch.closeFuture().sync();
        } finally {
            // Shut down executor threads to exit.
            group.shutdownGracefully();
        }
    }
}

