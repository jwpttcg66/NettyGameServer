package com.wolf.shoot.manager.spring;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.service.lookup.NetTcpSessionLoopUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jiangwenping on 17/3/1.
 * 本地spring会话服务
 */
@Repository
public class LocalSpringServiceManager {

    @Autowired
    private NetTcpSessionLoopUpService netTcpSessionLoopUpService;

    @Autowired
    private GameServerConfigService gameServerConfigService;

    public NetTcpSessionLoopUpService getNetTcpSessionLoopUpService() {
        return netTcpSessionLoopUpService;
    }

    public void setNetTcpSessionLoopUpService(NetTcpSessionLoopUpService netTcpSessionLoopUpService) {
        this.netTcpSessionLoopUpService = netTcpSessionLoopUpService;
    }

    public GameServerConfigService getGameServerConfigService() {
        return gameServerConfigService;
    }

    public void setGameServerConfigService(GameServerConfigService gameServerConfigService) {
        this.gameServerConfigService = gameServerConfigService;
    }

    public  void start() throws Exception {
        this.gameServerConfigService.startup();
    }

    public void stop() throws Exception{
        this.getGameServerConfigService().shutdown();
    }
}
