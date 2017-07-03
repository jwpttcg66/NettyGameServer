package com.snowcattle.game.service.net.http.handler.async;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.service.IService;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.net.http.NetHttpServerConfig;
import com.snowcattle.game.service.net.http.SdHttpServerConfig;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2017/7/3.
 */
@Service
public class AsyncNettyHttpHandlerService implements IService {

    /**
     * handler线程组
     */
    private DefaultEventExecutorGroup defaultEventExecutorGroup;

    @Override
    public String getId() {
        return ServiceName.AsyncNettyHttpHandlerService;
    }

    @Override
    public void startup() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        NetHttpServerConfig netHttpServerConfig = gameServerConfigService.getNetHttpServerConfig();
        SdHttpServerConfig sdHttpServerConfig = netHttpServerConfig.getSdHttpServerConfig();
        if(sdHttpServerConfig != null) {
            int threadSize = sdHttpServerConfig.getHandleThreadSize();
            defaultEventExecutorGroup = new DefaultEventExecutorGroup(threadSize);
        }
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

