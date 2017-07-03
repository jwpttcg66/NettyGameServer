package com.snowcattle.game.common.socket.message;

import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.NetMessageBody;
import com.snowcattle.game.service.message.NetMessageHead;
import com.snowcattle.game.service.message.decoder.NetTcpMessageDecoderFactory;
import com.snowcattle.game.service.message.encoder.NetMessageEncoderFactory;
import com.snowcattle.game.message.logic.tcp.online.client.OnlineHeartClientTcpMessage;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

/**
 * Created by jwp on 2017/1/26.
 */
public class NetMessageEncodeTest {

    public static void main(String[] args) throws Exception{
        AbstractNetMessage abstractNetMessage = new OnlineHeartClientTcpMessage();
        NetMessageHead netMessageHead = new NetMessageHead();
        netMessageHead.setSerial(5);
        netMessageHead.setCmd((short) 2);
        netMessageHead.setVersion((byte) 3);

        NetMessageBody netMessageBody = new NetMessageBody();
        byte[] bytes = "hello world".getBytes(CharsetUtil.UTF_8);
        netMessageBody.setBytes(bytes);

        netMessageHead.setLength(1+2+4+ bytes.length);
        abstractNetMessage.setNetMessageBody(netMessageBody);
        abstractNetMessage.setNetMessageHead(netMessageHead);

        ByteBuf byteBuf = new NetMessageEncoderFactory().createByteBuf(abstractNetMessage);
        System.out.println(byteBuf.array());

        //解析编码
        AbstractNetMessage decoderMesage = new NetTcpMessageDecoderFactory().praseMessage(byteBuf);
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
