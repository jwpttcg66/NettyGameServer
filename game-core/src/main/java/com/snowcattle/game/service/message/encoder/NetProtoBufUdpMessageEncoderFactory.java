package com.snowcattle.game.service.message.encoder;

import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.message.NetMessageBody;
import com.snowcattle.game.service.message.NetUdpMessageHead;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by jiangwenping on 17/2/20.
 */
public class NetProtoBufUdpMessageEncoderFactory implements INetProtoBufUdpMessageEncoderFactory {
    @Override
    public ByteBuf createByteBuf(AbstractNetProtoBufMessage netMessage) throws Exception {
        ByteBuf byteBuf = Unpooled.buffer(256);
        //编写head
        NetUdpMessageHead netMessageHead = (NetUdpMessageHead) netMessage.getNetMessageHead();
        byteBuf.writeShort(netMessageHead.getHead());
        //长度
        byteBuf.writeInt(0);
        //设置内容
        byteBuf.writeByte(netMessageHead.getVersion());
        byteBuf.writeShort(netMessageHead.getCmd());
        byteBuf.writeInt(netMessageHead.getSerial());
        //设置tockent
        byteBuf.writeLong(netMessageHead.getPlayerId());
        byteBuf.writeInt(netMessageHead.getTocken());

        //编写body
        netMessage.encodeNetProtoBufMessageBody();
        NetMessageBody netMessageBody = netMessage.getNetMessageBody();
        byteBuf.writeBytes(netMessageBody.getBytes());

        //重新设置长度
        int skip = 6;
        int length = byteBuf.readableBytes() - skip;
        byteBuf.setInt(2, length);
        byteBuf.slice();
        return byteBuf;
    }
}
