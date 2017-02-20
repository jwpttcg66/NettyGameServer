package com.wolf.shoot.service.net.message.encoder;

import com.wolf.shoot.service.net.message.AbstractNetProtoBufUdp2Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by jwp on 2017/2/16.
 */
public class NetProtoBufMessageUDPEncoder extends MessageToMessageEncoder<AbstractNetProtoBufUdp2Message> {

    private final Charset charset;

    private INetProtoBufUdpMessageEncoderFactory iNetMessageEncoderFactory;

    public NetProtoBufMessageUDPEncoder() {
        this(CharsetUtil.UTF_8);
        this.iNetMessageEncoderFactory = new NetProtoBufUdpMessageEncoderFactory();
    }

    public NetProtoBufMessageUDPEncoder(Charset charset) {
        if(charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractNetProtoBufUdp2Message msg, List<Object> out) throws Exception {
        ByteBuf netMessageBuf = iNetMessageEncoderFactory.createByteBuf(msg);
        out.add(new DatagramPacket(netMessageBuf, msg.getReceive()));
    }
}