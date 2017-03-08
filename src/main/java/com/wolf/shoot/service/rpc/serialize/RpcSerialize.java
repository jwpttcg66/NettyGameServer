package com.wolf.shoot.service.rpc.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jwp on 2017/3/8.
 */
public interface RpcSerialize {

    public <T> byte[] serialize(T obj);

    public <T> T deserialize(byte[] data, Class<T> cls);
}
