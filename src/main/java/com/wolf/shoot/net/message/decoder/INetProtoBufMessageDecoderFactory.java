package com.wolf.shoot.net.message.decoder;

import com.wolf.shoot.common.exception.CodecException;
import com.wolf.shoot.net.message.NetProtoBufMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by jiangwenping on 17/2/3.
 */
public interface INetProtoBufMessageDecoderFactory {
    public NetProtoBufMessage praseMessage(ByteBuf byteBuf) throws CodecException;
}
