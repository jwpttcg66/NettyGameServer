package com.wolf.shoot.net.message.decoder;

import com.wolf.shoot.net.message.NetMessageBody;
import com.wolf.shoot.net.message.NetMessageHead;
import com.wolf.shoot.net.message.NetProtoBufMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by jiangwenping on 17/2/3.
 */

public class NetProtoBufMessageDecoderFactory implements INetProtoBufMessageDecoderFactory{

    public NetProtoBufMessage praseMessage(ByteBuf byteBuf){
        NetProtoBufMessage netMessage = new NetProtoBufMessage();
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
//        netMessageBody.setBytes(bytes);


        //TODO 获取proto注册工程，解析proto
        netMessage.setNetMessageHead(netMessageHead);
//        netMessage.setNetMessageHead(netMessageBody);
        return netMessage;
    }
}
