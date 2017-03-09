package com.wolf.shoot.service.rpc.client.proxy;

import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.RpcRequest;
import com.wolf.shoot.service.rpc.client.ConnectManage;
import com.wolf.shoot.service.rpc.client.RPCFuture;
import com.wolf.shoot.service.rpc.client.RpcClientHandler;
import com.wolf.shoot.service.rpc.client.RpcRequestFactroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Created by luxiaoxun on 2016-03-16.
 */
public class ObjectProxy<T> implements InvocationHandler, IAsyncObjectProxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectProxy.class);
    private Class<T> clazz;

    public ObjectProxy(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return proxy == args[0];
            } else if ("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            } else if ("toString".equals(name)) {
                return proxy.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(proxy)) +
                        ", with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }

        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        // Debug
        LOGGER.debug(method.getDeclaringClass().getName());
        LOGGER.debug(method.getName());
        for (int i = 0; i < method.getParameterTypes().length; ++i) {
            LOGGER.debug(method.getParameterTypes()[i].getName());
        }
        for (int i = 0; i < args.length; ++i) {
            LOGGER.debug(args[i].toString());
        }

        RpcClientHandler handler = ConnectManage.getInstance().chooseHandler();
        RPCFuture rpcFuture = handler.sendRequest(request);
        return rpcFuture.get();
    }

//    @Override
//    public RPCFuture call(String funcName, Object... args) {
//        RpcClientHandler handler = ConnectManage.getInstance().chooseHandler();
//        RpcRequestFactroy rpcRequestFactroy = new RpcRequestFactroy();
//        RpcRequest request = rpcRequestFactroy.createRequest(this.clazz.getName(), funcName, args);
//        RPCFuture rpcFuture = handler.sendRequest(request);
//        return rpcFuture;
//    }

    @Override
    public RPCFuture createRpcFuture(RpcRequest rpcRequest) {
       return new RPCFuture(rpcRequest);
    }

    @Override
    public void asynCall(RPCFuture rpcFuture, RpcRequest rpcRequest) {
        RpcClientHandler handler = ConnectManage.getInstance().chooseHandler();
        handler.writeRequest(rpcFuture, rpcRequest);
    }
}
