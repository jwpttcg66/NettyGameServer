package com.wolf.shoot.service.rpc.client.proxy;

import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.RpcRequest;
import com.wolf.shoot.service.rpc.RpcContextHolder;
import com.wolf.shoot.service.rpc.RpcContextHolderObject;
import com.wolf.shoot.service.rpc.RpcServiceDiscovery;
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
        RpcContextHolderObject rpcContextHolderObject = RpcContextHolder.getContext();
        RpcServiceDiscovery rpcServiceDiscovery = LocalMananger.getInstance().getLocalSpringServiceManager().getRpcServiceDiscovery();
        RpcConnectManager rpcConnectManager = rpcServiceDiscovery.getRpcConnectMannger(rpcContextHolderObject.getBoEnum());
        RpcClient rpcClient = rpcConnectManager.chooseClient(rpcContextHolderObject.getServerId());
        RpcRequestFactroy rpcRequestFactroy = LocalMananger.getInstance().getLocalSpringBeanManager().getRequestFactroy();
        RpcRequest request = rpcRequestFactroy.createRequest(this.clazz.getName(), funcName, args);
        RPCFuture rpcFuture = rpcClient.sendRequest(request);
        return rpcFuture;
    }


}
