package com.wolf.shoot.net.message.encoder;

import com.wolf.shoot.net.message.AbstractAbstractNetProtoBufMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by jiangwenping on 17/2/8.
 */
public class NetProtoBufMessageTCPEncoder extends MessageToMessageEncoder<AbstractAbstractNetProtoBufMessage> {

    private final Charset charset;

    private INetProtoBufMessageEncoderFactory iNetMessageEncoderFactory;

    public NetProtoBufMessageTCPEncoder() {
        this(CharsetUtil.UTF_8);
        this.iNetMessageEncoderFactory = new NetProtoBufMessageEncoderFactory();
    }

    public NetProtoBufMessageTCPEncoder(Charset charset) {
        if(charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractAbstractNetProtoBufMessage msg, List<Object> out) throws Exception {
        ByteBuf netMessageBuf = iNetMessageEncoderFactory.createByteBuf(msg);
        out.add(netMessageBuf);
    }
}
