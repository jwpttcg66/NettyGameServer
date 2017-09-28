package com.snowcattle.game.service.message.decoder;

import com.snowcattle.game.common.exception.CodecException;
import com.snowcattle.game.service.message.AbstractNetProtoBufMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by jiangwenping on 2017/9/28.
 */
public interface INetProtoBufMessageDecoderFactory {
    public AbstractNetProtoBufMessage praseMessage(ByteBuf byteBuf) throws CodecException;
}
