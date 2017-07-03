package com.snowcattle.game.service.net.tcp.handler.async;

import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.IService;
import com.snowcattle.game.service.config.GameServerConfigService;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2017/5/22.
 * netty自带异步tcp handler服务
 */
@Service
public class AsyncNettyTcpHandlerService implements IService{

    /**
     * handler线程组
     */
    private DefaultEventExecutorGroup defaultEventExecutorGroup;

    @Override
    public String getId() {
        return ServiceName.AsyncTcpHandlerService;
    }

    @Override
    public void startup() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();

        int threadSize = gameServerConfig.getGameExcutorCorePoolSize();
        defaultEventExecutorGroup = new DefaultEventExecutorGroup(threadSize);
    }

    @Override
    public void shutdown() throws Exception {
        if(defaultEventExecutorGroup != null){
            defaultEventExecutorGroup.shutdownGracefully();
        }
    }

    public DefaultEventExecutorGroup getDefaultEventExecutorGroup() {
        return defaultEventExecutorGroup;
    }

    public void setDefaultEventExecutorGroup(DefaultEventExecutorGroup defaultEventExecutorGroup) {
        this.defaultEventExecutorGroup = defaultEventExecutorGroup;
    }
}
