package com.snowcattle.game.service.net.proxy.handler;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.net.tcp.session.NettyTcpSession;
import com.snowcattle.game.service.net.tcp.session.builder.NettyTcpSessionBuilder;
import com.snowcattle.game.service.net.proxy.ProxyRule;
import com.snowcattle.game.service.net.proxy.ProxyTcpBackChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;

/**
 * Created by jiangwenping on 2017/6/23.
 * 代理前端
 */
public class ProxyFrontendHandler extends ChannelInboundHandlerAdapter {

    /**
     * 代理产生的后端session
     */
    private NettyTcpSession proxySession;


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ProxyRule proxyRule = new ProxyRule();
        long serverId = 1;
        String host = "127.0.0.1";
        int port = 7090;
        proxyRule.setServerId(serverId);
        proxyRule.setRemoteHost(host);
        proxyRule.setRemotePort(port);

        this.proxySession = connectProxyRule(ctx, proxyRule);
//        // Start the connection attempt.
//        Bootstrap b = new Bootstrap();
//        b.group(inboundChannel.eventLoop())
//                .channel(ctx.channel().getClass())
//                .handler(new HexDumpProxyBackendHandler(inboundChannel))
//                .option(ChannelOption.AUTO_READ, false);
//        ChannelFuture f = b.connect(remoteHost, remotePort);
//        outboundChannel = f.channel();
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) {
//                if (future.isSuccess()) {
//                    // connection complete start to read first data
//                    inboundChannel.read();
//                } else {
//                    // Close the connection if the connection attempt has failed.
//                    inboundChannel.close();
//                }
//            }
//        });
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
        if (proxySession.isConnected()) {
            final Channel outboundChannel = proxySession.getChannel();
            outboundChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    if (future.isSuccess()) {
                        // was able to flush out data, start to read the next chunk
                        ctx.channel().read();
                    } else {
                        future.channel().close();
                    }
                }
            });
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (proxySession != null) {
            closeOnFlush(proxySession.getChannel());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
        closeOnFlush(ctx.channel());
    }

    /**
     * Closes the specified channel after all queued write requests are flushed.
     */
    public void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
//            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            ch.flush();
            ch.close();
        }
    }

    /**
     * 链接制定地址
     * @param ctx
     * @param proxyRule
     * @return
     */
    public NettyTcpSession connectProxyRule(final ChannelHandlerContext ctx, ProxyRule proxyRule){

        final Channel inboundChannel = ctx.channel();
        // Start the connection attempt.
        Bootstrap b = new Bootstrap();
        b.group(inboundChannel.eventLoop())
                .channel(ctx.channel().getClass()).handler(new ProxyTcpBackChannelInitializer(inboundChannel));
        b.option(ChannelOption.AUTO_READ, false);
        b.option(ChannelOption.TCP_NODELAY, true);

        ChannelFuture f = b.connect(proxyRule.getRemoteHost(), proxyRule.getRemotePort());
        Channel outboundChannel = f.channel();
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (future.isSuccess()) {
                    // connection complete start to read first data
                    inboundChannel.read();
                } else {
                    // Close the connection if the connection attempt has failed.
                    inboundChannel.close();
                }
            }
        });

        NettyTcpSessionBuilder nettyTcpSessionBuilder = LocalMananger.getInstance().getLocalSpringBeanManager().getNettyTcpSessionBuilder();
        NettyTcpSession nettyTcpSession = (NettyTcpSession) nettyTcpSessionBuilder.buildSession(outboundChannel);
        return  nettyTcpSession;
    }
}
