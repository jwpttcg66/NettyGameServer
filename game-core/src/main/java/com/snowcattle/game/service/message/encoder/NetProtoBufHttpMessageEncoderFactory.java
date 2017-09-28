package com.snowcattle.game.service.message.encoder;

import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import com.snowcattle.game.service.message.NetHttpMessageHead;
import com.snowcattle.game.service.message.NetMessageBody;
import com.snowcattle.game.service.message.NetMessageHead;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2017/9/28.
 * http编码
 */
@Service
public class NetProtoBufHttpMessageEncoderFactory implements INetProtoBufHttpMessageEncoderFactory {
    @Override
    public ByteBuf createByteBuf(AbstractNetProtoBufMessage netMessage) throws Exception {
        ByteBuf byteBuf = Unpooled.buffer(256);
        //编写head
        NetHttpMessageHead netMessageHead = (NetHttpMessageHead) netMessage.getNetMessageHead();
        byteBuf.writeShort(netMessageHead.getHead());
        //长度
        byteBuf.writeInt(0);
        //设置内容
        byteBuf.writeByte(netMessageHead.getVersion());
        byteBuf.writeShort(netMessageHead.getCmd());
        byteBuf.writeInt(netMessageHead.getSerial());
        //设置tocken
        byteBuf.writeLong(netMessageHead.getPlayerId());
        byte[] bytes = netMessageHead.getTocken().getBytes();
        byteBuf.writeShort(bytes.length);
        byteBuf.writeBytes(bytes);

        //编写body
        netMessage.encodeNetProtoBufMessageBody();
        NetMessageBody netMessageBody = netMessage.getNetMessageBody();
        byteBuf.writeBytes(netMessageBody.getBytes());

        //重新设置长度
        int skip = 6; //short + int
        int length = byteBuf.readableBytes() - skip;
        byteBuf.setInt(2, length);
        byteBuf.slice();
        return byteBuf;
    }
}
