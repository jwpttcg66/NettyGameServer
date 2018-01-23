package com.snowcattle.game.service.net.websocket.handler.async;

import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.service.IService;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.net.websocket.NetWebSocketServerConfig;
import com.snowcattle.game.service.net.websocket.SdWebSocketServerConfig;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2017/11/8.
 */

@Service
public class AsyncNettyWebSocketHandlerExecutorService implements IService {

    /**
     * handler线程组
     */
    private DefaultEventExecutorGroup defaultEventExecutorGroup;

    @Override
    public String getId() {
        return ServiceName.AsyncNettyWebSocketHandlerService;
    }

    @Override
    public void startup() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        NetWebSocketServerConfig netWebSocketServerConfig = gameServerConfigService.getNetWebSocketServerConfig();
        if(netWebSocketServerConfig != null) {
            SdWebSocketServerConfig sdWebSocketServerConfig = netWebSocketServerConfig.getSdWebSocketServerConfig();
            if (sdWebSocketServerConfig != null) {
                int threadSize = sdWebSocketServerConfig.getHandleThreadSize();
                defaultEventExecutorGroup = new DefaultEventExecutorGroup(threadSize);
            }
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