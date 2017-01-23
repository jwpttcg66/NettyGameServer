package com.wolf.shoot.socket.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * Created by jwp on 2017/1/23.
 */
public class EchoSocketClientHandler extends SimpleChannelInboundHandler<String> {

    public static final Logger utilLogger = LoggerFactory.getLogger("util");
    String req;

    public EchoSocketClientHandler() {
        req="请求";
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(req);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        utilLogger.debug("收到客户端数据" + s);
    }
}
