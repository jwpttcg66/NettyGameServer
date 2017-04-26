package com.wolf.shoot.service.rpc.client;


import com.wolf.shoot.common.annotation.RpcService;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.manager.LocalMananger;
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
public class RpcProxyService implements IService{

    private static ThreadPoolExecutor threadPoolExecutor;

    @SuppressWarnings("unchecked")
    public <T> T createProxy(Class<T> interfaceClass) {
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
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        int threadSize = gameServerConfigService.getGameServerConfig().getRpcSendProxyThreadSize();
        threadPoolExecutor = new ThreadPoolExecutor(threadSize, threadSize, 600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
    }

    @Override
    public void shutdown() throws Exception {
        threadPoolExecutor.shutdown();
    }

    /**
     * 如果是rpc接口的接口的话,
     * @param service
     * @return
     */
    public <T> T  createRemoteProxy (Object service, Class<T> interfaceClass){
        Class serviceClass = service.getClass();
        RpcService rpcService = (RpcService) serviceClass.getAnnotation(RpcService.class);
        if(rpcService != null){
            service = createProxy(interfaceClass);
            return (T) service;
        }
        return (T) service;
    }


    public <T> IAsyncRpcProxy createRemoteAsync(Object service, Class<T> interfaceClass) {
        return new AsyncRpcProxy<T>(interfaceClass);
    }
}

