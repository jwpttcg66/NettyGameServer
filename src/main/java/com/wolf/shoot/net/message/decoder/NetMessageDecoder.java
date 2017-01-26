package com.wolf.shoot.net.message.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by jwp on 2017/1/24.
 * 网络消息解码
 */
public class NetMessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    private INetMessageDecoderFactory iNetMessageDecoderFactory;

    public NetMessageDecoder(INetMessageDecoderFactory iNetMessageDecoderFactory) {
        this.iNetMessageDecoderFactory = iNetMessageDecoderFactory;
    }

    public INetMessageDecoderFactory getiNetMessageDecoderFactory() {
        return iNetMessageDecoderFactory;
    }

    public void setiNetMessageDecoderFactory(INetMessageDecoderFactory iNetMessageDecoderFactory) {
        this.iNetMessageDecoderFactory = iNetMessageDecoderFactory;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(iNetMessageDecoderFactory.praseMessage(msg));
    }
}
