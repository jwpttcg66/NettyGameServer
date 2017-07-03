package com.snowcattle.game.service.message.encoder;

import com.snowcattle.game.service.message.AbstractNetMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by jwp on 2017/1/26.
 */
public interface INetMessageEncoderFactory {
    public ByteBuf createByteBuf(AbstractNetMessage abstractNetMessage);
}
