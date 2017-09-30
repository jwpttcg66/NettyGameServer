package com.snowcattle.game.net.client.http;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.common.exception.CodecException;
import com.snowcattle.game.message.logic.http.client.OnlineHeartClientHttpMessage;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.message.decoder.NetProtoBufHttpMessageDecoderFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * Created by jiangwenping on 2017/9/29.
 */

public class GameHttpClientHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            System.err.println("STATUS: " + response.status());
            System.err.println("VERSION: " + response.protocolVersion());
            System.err.println();

            if (!response.headers().isEmpty()) {
                for (CharSequence name: response.headers().names()) {
                    for (CharSequence value: response.headers().getAll(name)) {
                        System.err.println("HEADER: " + name + " = " + value);
                    }
                }
                System.err.println();
            }

            if (HttpUtil.isTransferEncodingChunked(response)) {
                System.err.println("CHUNKED CONTENT {");
            } else {
                System.err.println("CONTENT {");
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;

            System.err.print(content.content().toString(CharsetUtil.UTF_8));
            System.err.flush();

            ByteBuf byteBuf = content.content();

            AbstractNetProtoBufMessage netProtoBufMessage = null;
            //开始解析
            NetProtoBufHttpMessageDecoderFactory netProtoBufHttpMessageDecoderFactory = new NetProtoBufHttpMessageDecoderFactory();
            try {
                netProtoBufMessage = netProtoBufHttpMessageDecoderFactory.praseMessage(byteBuf);
            } catch (CodecException e) {
                e.printStackTrace();
            }

            if(netProtoBufMessage instanceof OnlineHeartClientHttpMessage){
                OnlineHeartClientHttpMessage onlineHeartClientHttpMessage = (OnlineHeartClientHttpMessage) netProtoBufMessage;
                System.out.println(onlineHeartClientHttpMessage.getId());
            }

            if (content instanceof LastHttpContent) {
                System.err.println("} END OF CONTENT");
                ctx.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
