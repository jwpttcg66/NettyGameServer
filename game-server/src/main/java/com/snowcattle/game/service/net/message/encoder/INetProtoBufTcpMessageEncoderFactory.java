package com.snowcattle.game.service.net.message.encoder;

import com.snowcattle.game.service.net.message.AbstractNetProtoBufMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by jiangwenping on 17/2/8.
 */
public interface INetProtoBufTcpMessageEncoderFactory {
    public ByteBuf createByteBuf(AbstractNetProtoBufMessage netMessage) throws Exception;
}
