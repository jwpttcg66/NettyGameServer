package com.wolf.shoot.service.rpc.client;


import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.rpc.client.proxy.AsyncRpcProxy;
import com.wolf.shoot.service.rpc.client.proxy.IAsyncRpcProxy;
import com.wolf.shoot.service.rpc.client.proxy.ObjectProxy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Proxy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * RPC Client（Create RPC proxy）
 */
@Service
public class RpcSenderProxy implements IService{

    private static ThreadPoolExecutor threadPoolExecutor;

    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new ObjectProxy<T>(interfaceClass)
        );
    }

    public <T> IAsyncRpcProxy createAsync(Class<T> interfaceClass) {
        return new AsyncRpcProxy<T>(interfaceClass);
    }

    public void submit(Runnable task){
        threadPoolExecutor.submit(task);
    }

    @Override
    public String getId() {
        return ServiceName.RpcSenderProxy;
    }

    @Override
    public void startup() throws Exception {
        threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
    }

    @Override
    public void shutdown() throws Exception {
        threadPoolExecutor.shutdown();
    }
}

