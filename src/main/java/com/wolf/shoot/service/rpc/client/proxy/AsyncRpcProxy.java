package com.wolf.shoot.service.rpc.client.proxy;

import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.RpcRequest;
import com.wolf.shoot.service.rpc.client.*;

/**
 * Created by jwp on 2017/3/9.
 */


public class AsyncRpcProxy<T> implements IAsyncRpcProxy{

    private Class<T> clazz;

    public AsyncRpcProxy(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public RPCFuture call(String funcName, Object... args) {
        RpcClientHandler handler = ConnectManage.getInstance().chooseHandler();
        RpcRequestFactroy rpcRequestFactroy = new RpcRequestFactroy();
        RpcRequest request = rpcRequestFactroy.createRequest(this.clazz.getName(), funcName, args);
        RPCFuture rpcFuture = handler.sendRequest(request);
        return rpcFuture;
    }


}
