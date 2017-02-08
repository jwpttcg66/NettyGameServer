package com.wolf.shoot.net.message.encoder;

import com.wolf.shoot.net.message.NetProtoBufMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by jiangwenping on 17/2/8.
 */
public interface INetProtoBufMessageEncoderFactory {
    public ByteBuf createByteBuf(NetProtoBufMessage netMessage) throws Exception;
}
