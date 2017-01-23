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
public class EchoClientHandler2 extends SimpleChannelInboundHandler<String> {

    public static final Logger utilLogger = LoggerFactory.getLogger("util");
    byte[] req;

    public EchoClientHandler2() {
        req=("我是请求数据哦2"+System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message=null;
        for(int i=0;i<1;i++){
            message= Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        utilLogger.debug("收到客户端数据" + s);
    }
}
