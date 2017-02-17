package com.wolf.shoot.net.message.decoder;

import com.wolf.shoot.net.message.AbstractNetMessage;
import com.wolf.shoot.net.message.NetMessageBody;
import com.wolf.shoot.net.message.NetMessageHead;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by jwp on 2017/1/24.
 */
public class NetMessageDecoderFactory implements INetMessageDecoderFactory{

    public AbstractNetMessage praseMessage(ByteBuf byteBuf){
        AbstractNetMessage abstractNetMessage = new AbstractNetMessage();
        //读取head
        NetMessageHead netMessageHead = new NetMessageHead();
        //head为两个字节，跳过
        byteBuf.skipBytes(2);
        netMessageHead.setLength(byteBuf.readInt());
        netMessageHead.setVersion(byteBuf.readByte());
        netMessageHead.setCmd(byteBuf.readShort());
        netMessageHead.setSerial(byteBuf.readInt());
        //读取body
        NetMessageBody netMessageBody = new NetMessageBody();
        int byteLength = byteBuf.readableBytes();
        ByteBuf bodyByteBuffer = Unpooled.buffer(256);
        byte[] bytes = new byte[byteLength];
        bodyByteBuffer = byteBuf.getBytes(byteBuf.readerIndex(), bytes);
        netMessageBody.setBytes(bytes);

        abstractNetMessage.setNetMessageHead(netMessageHead);
        abstractNetMessage.setNetMessageBody(netMessageBody);
        return abstractNetMessage;
    }
}
