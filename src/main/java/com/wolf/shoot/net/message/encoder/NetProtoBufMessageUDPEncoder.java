package com.wolf.shoot.net.message.encoder;

import com.wolf.shoot.net.message.NetProtoBufUDPMessage;
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
public class NetProtoBufMessageUDPEncoder extends MessageToMessageEncoder<NetProtoBufUDPMessage> {

    private final Charset charset;

    private INetProtoBufMessageEncoderFactory iNetMessageEncoderFactory;

    public NetProtoBufMessageUDPEncoder() {
        this(CharsetUtil.UTF_8);
        this.iNetMessageEncoderFactory = new NetProtoBufMessageEncoderFactory();
    }

    public NetProtoBufMessageUDPEncoder(Charset charset) {
        if(charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NetProtoBufUDPMessage msg, List<Object> out) throws Exception {
        ByteBuf netMessageBuf = iNetMessageEncoderFactory.createByteBuf(msg);
        out.add(new DatagramPacket(netMessageBuf, msg.getReceive()));
    }
}