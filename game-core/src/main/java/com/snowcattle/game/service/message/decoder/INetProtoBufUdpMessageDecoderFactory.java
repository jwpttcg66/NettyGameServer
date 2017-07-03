package com.snowcattle.game.service.message.decoder;

import com.snowcattle.game.common.exception.CodecException;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by jiangwenping on 17/2/20.
 */
public interface INetProtoBufUdpMessageDecoderFactory {
    public AbstractNetProtoBufMessage praseMessage(ByteBuf byteBuf) throws CodecException;
}
