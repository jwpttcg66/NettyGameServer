package com.snowcattle.game.service.rpc.client.proxy;

import com.snowcattle.game.service.rpc.client.RPCFuture;

/**
 * Created by jwp on 2017/3/9.
 */
public interface IAsyncRpcProxy {
    public RPCFuture call(String funcName, Object... args);
}
