package com.wolf.shoot.service.net.message.encoder;

import com.wolf.shoot.service.net.message.AbstractNetProtoBufMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by jiangwenping on 17/2/8.
 */
public interface INetProtoBufMessageEncoderFactory {
    public ByteBuf createByteBuf(AbstractNetProtoBufMessage netMessage) throws Exception;
}
