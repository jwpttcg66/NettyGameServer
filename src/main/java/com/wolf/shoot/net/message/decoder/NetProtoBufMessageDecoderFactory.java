package com.wolf.shoot.net.message.decoder;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.exception.CodecException;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.net.message.NetMessageHead;
import com.wolf.shoot.net.message.NetProtoBufMessage;
import com.wolf.shoot.net.message.NetProtoBufMessageBody;
import com.wolf.shoot.net.message.registry.MessageRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import sun.rmi.runtime.Log;

import java.util.logging.Logger;

/**
 * Created by jiangwenping on 17/2/3.
 */

public class NetProtoBufMessageDecoderFactory implements INetProtoBufMessageDecoderFactory{

    public NetProtoBufMessage praseMessage(ByteBuf byteBuf) throws CodecException {
        //读取head
        NetMessageHead netMessageHead = new NetMessageHead();
        //head为两个字节，跳过
        byteBuf.skipBytes(2);
        netMessageHead.setLength(byteBuf.readInt());
        netMessageHead.setVersion(byteBuf.readByte());
        short cmd = byteBuf.readShort();
        netMessageHead.setCmd(cmd);
        netMessageHead.setSerial(byteBuf.readInt());

        MessageRegistry messageRegistry = LocalMananger.getInstance().get(MessageRegistry.class);
        NetProtoBufMessage netMessage = messageRegistry.getMessage(cmd);
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
