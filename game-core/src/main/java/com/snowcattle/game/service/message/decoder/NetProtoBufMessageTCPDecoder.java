package com.snowcattle.game.service.message.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by jiangwenping on 17/2/3.
 */

public class NetProtoBufMessageTCPDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final Charset charset;

    private INetProtoBufTcpMessageDecoderFactory iNetMessageDecoderFactory;

    public NetProtoBufMessageTCPDecoder() {
        this(CharsetUtil.UTF_8);
        this.iNetMessageDecoderFactory = new NetProtoBufTcpMessageDecoderFactory();
    }

    public NetProtoBufMessageTCPDecoder(Charset charset) {
        if(charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }



    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(iNetMessageDecoderFactory.praseMessage(msg));
    }
}

