package com.wolf.shoot.net.message.encoder;

import com.wolf.shoot.net.message.NetMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by jwp on 2017/1/24.
 */
public class NetMessageFactory {

    public static ByteBuf createByteBuf(NetMessage netMessage){
        ByteBuf byteBuf = Unpooled.buffer(1024);
        return byteBuf;
    }
}
