package com.snowcattle.game.service.proxy.handler;

import com.snowcattle.game.service.net.session.NettyTcpSession;
import com.snowcattle.game.service.proxy.ProxyRule;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by jiangwenping on 2017/6/23.
 * 代理前端
 */
public class ProxyFrontendHandler extends ChannelInboundHandlerAdapter {

    /**
     * 代理规则
     */
    private ProxyRule proxyRule;

    /**
     * 代理产生的后端session
     */
    private NettyTcpSession proxySession;

    public ProxyFrontendHandler(ProxyRule proxyRule) {
        this.proxyRule = proxyRule;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        final Channel inboundChannel = ctx.channel();

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
//        if (outboundChannel.isActive()) {
//            outboundChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
//                @Override
//                public void operationComplete(ChannelFuture future) {
//                    if (future.isSuccess()) {
//                        // was able to flush out data, start to read the next chunk
//                        ctx.channel().read();
//                    } else {
//                        future.channel().close();
//                    }
//                }
//            });
//        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
//        if (outboundChannel != null) {
//            closeOnFlush(outboundChannel);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        closeOnFlush(ctx.channel());
    }

    /**
     * Closes the specified channel after all queued write requests are flushed.
     */
    static void closeOnFlush(Channel ch) {
//        if (ch.isActive()) {
//            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
//        }
    }
}
