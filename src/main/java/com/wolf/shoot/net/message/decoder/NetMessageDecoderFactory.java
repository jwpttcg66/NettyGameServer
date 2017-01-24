package com.wolf.shoot.net.message.decoder;

import com.wolf.shoot.net.message.NetMessage;
import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;

/**
 * Created by jwp on 2017/1/24.
 */
public class NetMessageDecoderFactory {

    public static NetMessage praseMessage(ByteBuf byteBuf){
        NetMessage netMessage = new NetMessage();

        return netMessage;
    }
}
