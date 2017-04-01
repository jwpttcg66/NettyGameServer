package com.wolf.shoot.service.rpc.server;

import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.common.util.ExecutorUtil;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/3/8.
 */
@Service
public class RemoteRpcHandlerService implements IService{

    @Autowired
    private RpcThreadPool rpcThreadPool;

    @Override
    public String getId() {
        return ServiceName.RemoteRpcService;
    }

    @Override
    public void startup() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        if(gameServerConfig.isRpcFlag()){
            //开启服务
            rpcThreadPool.createExecutor(gameServerConfig.getRpcThreadPoolSize(), gameServerConfig.getRpcThreadPoolQueueSize());
        }
    }

    @Override
    public void shutdown() throws Exception {
        ExecutorUtil.shutdownAndAwaitTermination(rpcThreadPool.getExcutor());
    }

    public void submit(Runnable runnable){
        rpcThreadPool.getExcutor().submit(runnable);
    }
}
