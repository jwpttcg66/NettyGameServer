package com.wolf.shoot.service.rpc.client.proxy;

import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.RpcRequest;
import com.wolf.shoot.service.rpc.client.RpcContextHolder;
import com.wolf.shoot.service.rpc.client.RpcContextHolderObject;
import com.wolf.shoot.service.rpc.client.RpcClientConnectService;
import com.wolf.shoot.service.rpc.client.*;
import com.wolf.shoot.service.rpc.client.net.RpcClient;

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
        RpcClientConnectService rpcClientConnectService = LocalMananger.getInstance().getLocalSpringServicerAfterManager().getRpcClientConnectService();
        AbstractRpcConnectManager abstractRpcConnectManager = rpcClientConnectService.getRpcConnectMannger(rpcContextHolderObject.getBoEnum());
        RpcClient rpcClient = abstractRpcConnectManager.chooseClient(rpcContextHolderObject.getServerId());
        RpcRequestFactory rpcRequestFactory = LocalMananger.getInstance().getLocalSpringBeanManager().getRequestFactory();
        RpcRequest request = rpcRequestFactory.createRequest(this.clazz.getName(), funcName, args);
        RPCFuture rpcFuture = rpcClient.sendRequest(request);
        return rpcFuture;
    }


}
