package com.wolf.shoot.net.message.encoder;

import com.wolf.shoot.net.message.NetMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by jwp on 2017/1/24.
 */
public class NetMessageEncoder extends MessageToMessageEncoder<NetMessage> {

    private final Charset charset;

    public NetMessageEncoder() {
        this(CharsetUtil.UTF_8);
    }

    public NetMessageEncoder(Charset charset) {
        if(charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NetMessage msg, List<Object> out) throws Exception {
        ByteBuf netMessageBuf = NetMessageEncoderFactory.createByteBuf(msg);
        out.add(netMessageBuf);
    }
}
