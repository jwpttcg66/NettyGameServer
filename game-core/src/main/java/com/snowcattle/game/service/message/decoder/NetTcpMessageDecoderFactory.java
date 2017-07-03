package com.snowcattle.game.service.message.decoder;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.NetMessageBody;
import com.snowcattle.game.service.message.NetMessageHead;
import com.snowcattle.game.service.message.registry.MessageRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by jwp on 2017/1/24.
 */
public class NetTcpMessageDecoderFactory implements INetMessageDecoderFactory{

    public AbstractNetMessage praseMessage(ByteBuf byteBuf){
        //读取head
        NetMessageHead netMessageHead = new NetMessageHead();
        //head为两个字节，跳过
        byteBuf.skipBytes(2);
        netMessageHead.setLength(byteBuf.readInt());
        netMessageHead.setVersion(byteBuf.readByte());
        int cmd = byteBuf.readShort();
        netMessageHead.setCmd(byteBuf.readShort());
        netMessageHead.setSerial(byteBuf.readInt());
        //读取body
        NetMessageBody netMessageBody = new NetMessageBody();
        int byteLength = byteBuf.readableBytes();
        ByteBuf bodyByteBuffer = Unpooled.buffer(256);
        byte[] bytes = new byte[byteLength];
        bodyByteBuffer = byteBuf.getBytes(byteBuf.readerIndex(), bytes);
        netMessageBody.setBytes(bytes);

        MessageRegistry messageRegistry = LocalMananger.getInstance().getLocalSpringServiceManager().getMessageRegistry();
        AbstractNetMessage abstractNetMessage = messageRegistry.getMessage(cmd);
        abstractNetMessage.setNetMessageHead(netMessageHead);
        abstractNetMessage.setNetMessageBody(netMessageBody);
        return abstractNetMessage;
    }
}
