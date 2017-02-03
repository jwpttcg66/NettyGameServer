package com.wolf.shoot.net.message.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by jiangwenping on 17/2/3.
 */

public class NetProtoBufDecoder extends MessageToMessageDecoder<ByteBuf> {

    private INetMessageDecoderFactory iNetMessageDecoderFactory;

    public NetProtoBufDecoder(INetMessageDecoderFactory iNetMessageDecoderFactory) {
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

