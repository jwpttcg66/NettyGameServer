package com.snowcattle.game.service.message.encoder;

import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by jiangwenping on 2017/9/28.
 */
public interface INetProtoBufMessageEncoderFactory {
    public ByteBuf createByteBuf(AbstractNetProtoBufMessage netMessage) throws Exception;
}
