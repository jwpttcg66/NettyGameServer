package com.snowcattle.game.service.rpc.client.proxy;

import com.snowcattle.game.service.rpc.client.*;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.net.tcp.RpcRequest;
import com.snowcattle.game.service.rpc.client.RpcClientConnectService;
import com.snowcattle.game.service.rpc.client.net.RpcClient;

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
