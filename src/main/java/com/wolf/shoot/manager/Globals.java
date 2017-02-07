package com.wolf.shoot.manager;

import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.config.GameServerDiffConfig;
import com.wolf.shoot.service.net.GameNettyTcpServerService;
import com.wolf.shoot.service.time.SystemTimeService;
import com.wolf.shoot.service.time.TimeService;

/**
 * Created by jiangwenping on 17/2/7.
 * 各种全局的业务管理器、公共服务实例的持有者，负责各种管理器的初始化和实例的获取
 */
public class Globals {

    /**
     * tcp服务器
     */
    private static GameNettyTcpServerService gameNettyTcpServerService;

    /**
     * 服务器启动时调用，初始化所有管理器实例
     * @param configFile
     * @throws Exception
     */
    public static void init(String configFile) throws Exception {

        LocalMananger.getInstance().create(GameServerConfigService.class, GameServerConfigService.class);
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        GameServerDiffConfig gameServerDiffConfig = gameServerConfigService.getGameServerDiffConfig();

        //时间服务
        LocalMananger.getInstance().create(SystemTimeService.class, TimeService.class);

        gameNettyTcpServerService = new GameNettyTcpServerService(gameServerConfig.getServerId(), gameServerConfig.getPort());
    }
}
