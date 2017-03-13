package com.wolf.shoot.service.rpc.client.proxy;

import com.wolf.shoot.service.net.RpcRequest;
import com.wolf.shoot.service.rpc.RpcContextHolder;
import com.wolf.shoot.service.rpc.client.ConnectManage;
import com.wolf.shoot.service.rpc.client.RPCFuture;
import com.wolf.shoot.service.rpc.client.RpcClientHandler;
import com.wolf.shoot.service.rpc.client.RpcRequestFactroy;

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
        String serverId = RpcContextHolder.getServer();
        RpcClientHandler handler = ConnectManage.getInstance().chooseHandler(serverId);
        RpcRequestFactroy rpcRequestFactroy = new RpcRequestFactroy();
        RpcRequest request = rpcRequestFactroy.createRequest(this.clazz.getName(), funcName, args);
        RPCFuture rpcFuture = handler.sendRequest(request);
        return rpcFuture;
    }


}
