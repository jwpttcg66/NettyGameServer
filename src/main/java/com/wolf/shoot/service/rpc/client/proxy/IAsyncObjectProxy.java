package com.wolf.shoot.service.rpc.client.proxy;


import com.wolf.shoot.service.rpc.client.RPCFuture;

/**
 * Created by luxiaoxun on 2016/3/16.
 */
public interface IAsyncObjectProxy {
    public RPCFuture call(String funcName, Object... args);
}