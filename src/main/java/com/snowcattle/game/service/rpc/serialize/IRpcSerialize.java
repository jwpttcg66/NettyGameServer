package com.snowcattle.game.service.rpc.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Created by jwp on 2017/3/8.
 * rpc对象序列化
 */
public interface IRpcSerialize {

    public <T> byte[] serialize(T obj);

    public <T> T deserialize(byte[] data, Class<T> cls);
}
