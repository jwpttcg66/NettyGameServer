package com.snowcattle.game.service.message.decoder;

import com.snowcattle.game.service.message.AbstractNetProtoBufUdpMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by jwp on 2017/2/16.
 */
public class NetProtoBufMessageUDPDecoder extends MessageToMessageDecoder<DatagramPacket> {

    private final Charset charset;

    private INetProtoBufUdpMessageDecoderFactory iNetMessageDecoderFactory;

    public NetProtoBufMessageUDPDecoder() {
        this(CharsetUtil.UTF_8);
        this.iNetMessageDecoderFactory = new NetProtoBufUdpMessageDecoderFactory();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        AbstractNetProtoBufUdpMessage netProtoBufUDPMessage = (AbstractNetProtoBufUdpMessage) iNetMessageDecoderFactory.praseMessage(msg.content());
        netProtoBufUDPMessage.setSend(msg.sender());
        out.add(netProtoBufUDPMessage);
    }

    public NetProtoBufMessageUDPDecoder(Charset charset) {
        if(charset == null) {
            throw new NullPointerException("charset");
        } else {
            this.charset = charset;
        }
    }
}