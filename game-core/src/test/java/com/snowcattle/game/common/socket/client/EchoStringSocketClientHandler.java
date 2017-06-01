package com.snowcattle.game.common.socket.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jwp on 2017/1/23.
 */
public class EchoStringSocketClientHandler extends SimpleChannelInboundHandler<String> {

    public static final Logger utilLogger = LoggerFactory.getLogger("util");
    String req;
    int count;
    public EchoStringSocketClientHandler() {
        req="请求";
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        count++;
        ctx.writeAndFlush(req + count+"\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        utilLogger.debug("客户端收到服务器数据： " + s);
        count++;
        channelHandlerContext.writeAndFlush(req + count+"\n");
    }
}
