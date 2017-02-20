package com.wolf.shoot.service.net.message.decoder;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.exception.CodecException;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.message.AbstractNetProtoBufMessage;
import com.wolf.shoot.service.net.message.NetProtoBufMessageBody;
import com.wolf.shoot.service.net.message.NetUdpMessageHead;
import com.wolf.shoot.service.net.message.registry.MessageRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by jiangwenping on 17/2/20.
 */
public class NetProtoBufUdpMessageDecoderFactory implements INetProtoBufUdpMessageDecoderFactory{
    @Override
    public AbstractNetProtoBufMessage praseMessage(ByteBuf byteBuf) throws CodecException {
        //读取head
        NetUdpMessageHead netMessageHead = new NetUdpMessageHead();
        //head为两个字节，跳过
        byteBuf.skipBytes(2);
        netMessageHead.setLength(byteBuf.readInt());
        netMessageHead.setVersion(byteBuf.readByte());

        //读取内容
        short cmd = byteBuf.readShort();
        netMessageHead.setCmd(cmd);
        netMessageHead.setSerial(byteBuf.readInt());
        
        //读取tocken
        netMessageHead.setPlayerId(byteBuf.readLong());
        netMessageHead.setTocken(byteBuf.readInt());

        MessageRegistry messageRegistry = LocalMananger.getInstance().get(MessageRegistry.class);
        AbstractNetProtoBufMessage netMessage = messageRegistry.getMessage(cmd);
        //读取body
        NetProtoBufMessageBody netMessageBody = new NetProtoBufMessageBody();
        int byteLength = byteBuf.readableBytes();
        ByteBuf bodyByteBuffer = Unpooled.buffer(256);
        byte[] bytes = new byte[byteLength];
        bodyByteBuffer = byteBuf.getBytes(byteBuf.readerIndex(), bytes);
        netMessageBody.setBytes(bytes);
        netMessage.setNetMessageHead(netMessageHead);
        netMessage.setNetMessageBody(netMessageBody);
        try {
            netMessage.decoderNetProtoBufMessageBody();
            netMessage.releaseMessageBody();
        }catch (Exception e){
            throw new CodecException("message cmd " + cmd + "decoder error", e);
        }

        //增加协议解析打印
        if(Loggers.sessionLogger.isDebugEnabled()){
            Loggers.sessionLogger.debug("revice net message" + netMessage.toAllInfoString());
        }

        return netMessage;
    }
}
