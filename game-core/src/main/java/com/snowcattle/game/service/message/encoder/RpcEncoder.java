package com.snowcattle.game.service.message.encoder;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.rpc.serialize.IRpcSerialize;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by jwp on 2017/3/7.
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            IRpcSerialize IRpcSerialize = LocalMananger.getInstance().getLocalSpringBeanManager().getProtostuffSerialize();
            byte[] data = IRpcSerialize.serialize(in);
            //byte[] data = JsonUtil.serialize(in); // Not use this, have some bugs
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}