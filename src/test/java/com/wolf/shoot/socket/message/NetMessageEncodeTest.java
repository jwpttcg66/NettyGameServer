package com.wolf.shoot.socket.message;

import com.wolf.shoot.net.message.NetMessage;
import com.wolf.shoot.net.message.NetMessageBody;
import com.wolf.shoot.net.message.NetMessageHead;
import com.wolf.shoot.net.message.decoder.NetMessageDecoderFactory;
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
        netMessageHead.setSerial(5);
        netMessageHead.setCmd((short) 2);
        netMessageHead.setVersion((byte) 3);

        NetMessageBody netMessageBody = new NetMessageBody();
        byte[] bytes = "hello world".getBytes(CharsetUtil.UTF_8);
        netMessageBody.setBytes(bytes);

        netMessageHead.setLength(1+2+4+ bytes.length);
        netMessage.setNetMessageBody(netMessageBody);
        netMessage.setNetMessageHead(netMessageHead);

        ByteBuf byteBuf = NetMessageEncoderFactory.createByteBuf(netMessage);
        System.out.println(byteBuf.array());

        //解析编码
        NetMessage decoderMesage = NetMessageDecoderFactory.praseMessage(byteBuf);
        netMessageHead = decoderMesage.getNetMessageHead();
        netMessageBody = decoderMesage.getNetMessageBody();
        System.out.println(netMessageHead.getSerial());
        System.out.println(netMessageHead.getCmd());
        System.out.println(netMessageHead.getVersion());
        String requestString = new String(netMessageBody.getBytes(), CharsetUtil.UTF_8);
        System.out.println(requestString);
        System.out.println(decoderMesage);
    }
}
