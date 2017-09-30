package com.snowcattle.game.service.message.decoder;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.exception.CodecException;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.message.NetHttpMessageHead;
import com.snowcattle.game.service.message.NetProtoBufMessageBody;
import com.snowcattle.game.service.message.NetUdpMessageHead;
import com.snowcattle.game.service.message.registry.MessageRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2017/9/28.
 */
@Service
public class NetProtoBufHttpMessageDecoderFactory implements INetProtoBufHttpMessageDecoderFactory{
    @Override
    public AbstractNetProtoBufMessage praseMessage(ByteBuf byteBuf) throws CodecException {
        //读取head
        NetHttpMessageHead netMessageHead = new NetHttpMessageHead();
        //head为两个字节，跳过
        byteBuf.skipBytes(2);
        netMessageHead.setLength(byteBuf.readInt());
        netMessageHead.setVersion(byteBuf.readByte());

        //读取内容
        short cmd = byteBuf.readShort();
        netMessageHead.setCmd(cmd);
        netMessageHead.setSerial(byteBuf.readInt());

        netMessageHead.setPlayerId(byteBuf.readLong());
        //读取tocken
        short tockenLength = byteBuf.readShort();
        byte[] tockenBytes = new byte[tockenLength];
        ByteBuf tockenBuf = byteBuf.readBytes(tockenBytes);
        netMessageHead.setTocken(tockenBuf.toString());

        MessageRegistry messageRegistry = LocalMananger.getInstance().getLocalSpringServiceManager().getMessageRegistry();
        AbstractNetProtoBufMessage netMessage = messageRegistry.getMessage(cmd);
        //读取body
        NetProtoBufMessageBody netMessageBody = new NetProtoBufMessageBody();
        int byteLength = byteBuf.readableBytes();
        ByteBuf bodyByteBuffer = null;
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
