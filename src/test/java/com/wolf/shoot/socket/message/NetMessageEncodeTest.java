package com.wolf.shoot.socket.message;

import com.wolf.shoot.net.message.NetMessage;
import com.wolf.shoot.net.message.NetMessageBody;
import com.wolf.shoot.net.message.NetMessageHead;
import com.wolf.shoot.net.message.encoder.NetMessageEncoderFactory;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

/**
 * Created by jwp on 2017/1/26.
 */
public class NetMessageEncodeTest {

    public static void main(String[] args) throws Exception{
        NetMessage netMessage = new NetMessage();
        NetMessageHead netMessageHead = new NetMessageHead();
        netMessageHead.setSerial(1);
        netMessageHead.setCmd((short) 1);
        netMessageHead.setVersion((byte) 1);

        NetMessageBody netMessageBody = new NetMessageBody();
        byte[] bytes = "hello world".getBytes(CharsetUtil.UTF_8);
        netMessageBody.setBytes(bytes);

        netMessage.setNetMessageBody(netMessageBody);
        netMessage.setNetMessageHead(netMessageHead);

        ByteBuf byteBuf = NetMessageEncoderFactory.createByteBuf(netMessage);
        System.out.println(byteBuf.array());
    }
}
