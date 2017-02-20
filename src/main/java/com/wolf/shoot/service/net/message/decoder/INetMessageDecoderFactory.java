package com.wolf.shoot.service.net.message.decoder;

import com.wolf.shoot.service.net.message.AbstractNetMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by jwp on 2017/1/26.
 */
public interface INetMessageDecoderFactory {
    public AbstractNetMessage praseMessage(ByteBuf byteBuf);
}
