package com.snowcattle.game.service.message.encoder;

import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by jiangwenping on 17/2/20.
 */
public interface INetProtoBufUdpMessageEncoderFactory {
    public ByteBuf createByteBuf(AbstractNetProtoBufMessage netMessage) throws Exception;
}
