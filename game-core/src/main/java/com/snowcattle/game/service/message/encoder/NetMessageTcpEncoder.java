package com.snowcattle.game.service.message.encoder;

import com.snowcattle.game.service.message.AbstractNetMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by jwp on 2017/1/24.
 */
public class NetMessageTcpEncoder extends MessageToMessageEncoder<AbstractNetMessage> {

    private final Charset charset;

    private INetMessageEncoderFactory iNetMessageEncoderFactory;

    public NetMessageTcpEncoder() {
        this(CharsetUtil.UTF_8);
        this.iNetMessageEncoderFactory = new NetMessageEncoderFactory();
    }

    public NetMessageTcpEncoder(Charset charset) {
        if(charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractNetMessage msg, List<Object> out) throws Exception {
        ByteBuf netMessageBuf = iNetMessageEncoderFactory.createByteBuf(msg);
        out.add(netMessageBuf);
    }
}
