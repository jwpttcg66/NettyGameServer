package com.snowcattle.game.service.rpc.serialize;

/**
 * Created by jwp on 2017/3/8.
 * rpc对象序列化
 */
public interface IRpcSerialize {

    public <T> byte[] serialize(T obj);

    public <T> T deserialize(byte[] data, Class<T> cls);
}
