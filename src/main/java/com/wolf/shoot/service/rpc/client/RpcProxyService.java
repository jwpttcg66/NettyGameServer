package com.wolf.shoot.service.rpc.client;


import com.wolf.shoot.common.annotation.RpcServiceAnnotation;
import com.wolf.shoot.common.annotation.RpcServiceBoEnum;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.BOEnum;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.rpc.client.proxy.AsyncRpcProxy;
import com.wolf.shoot.service.rpc.client.proxy.IAsyncRpcProxy;
import com.wolf.shoot.service.rpc.client.proxy.ObjectProxy;
import com.wolf.shoot.service.rpc.server.RpcConfig;
import com.wolf.shoot.service.rpc.server.RpcMethodRegistry;
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
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        int timeOut = gameServerConfigService.getGameServerConfig().getRpcTimeOut();
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new ObjectProxy<T>(interfaceClass, timeOut)
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
     * 如果本机已经提供了远程对应的rpc服务，进行本地调用
     * @param interfaceClass
     * @return
     */
    public <T> T  createRemoteProxy (Class<T> interfaceClass){
        RpcMethodRegistry rpcMethodRegistry = LocalMananger.getInstance().getLocalSpringServiceManager().getRpcMethodRegistry();
        String serviceName = interfaceClass.getName();
        Object bean = rpcMethodRegistry.getServiceBean(serviceName);
        if (bean == null){
            //如果是空，进行rpc调用
            return  null;
        }

        RpcServiceAnnotation rpcServiceAnnotation = bean.getClass().getAnnotation(RpcServiceAnnotation.class);
        if(rpcServiceAnnotation == null){
            //找不到rpc服务
            return null;
        }

        RpcServiceBoEnum rpcServiceBoEnum = bean.getClass().getAnnotation(RpcServiceBoEnum.class);
        if(rpcServiceBoEnum == null){
            //找不到rpc服务
            return null;
        }

        //是否本地提供服务
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        RpcConfig rpcConfig = gameServerConfigService.getRpcConfig();
        BOEnum boEnum = rpcServiceBoEnum.bo();
        if(rpcConfig.getSdRpcServiceProvider().validServer(boEnum.getBoId())){
            return (T) bean;
        }

        return createProxy(interfaceClass);
    }

}

