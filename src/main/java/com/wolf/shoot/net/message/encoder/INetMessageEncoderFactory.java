package com.wolf.shoot.net.message.encoder;

import com.wolf.shoot.net.message.NetMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by jwp on 2017/1/26.
 */
public interface INetMessageEncoderFactory {
    public ByteBuf createByteBuf(NetMessage netMessage);
}
