package com.wolf.shoot.service.net;

import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;

/**
 * Created by jiangwenping on 17/2/15.
 */
public class LocalNetService implements IService{

    private GameNettyTcpServerService gameNettyTcpServerService;
    private GameNettyUdpServerService gameNettyUdpServerService;
    private GameNettyRPCService gameNettyRPCService;

    @Override
    public String getId() {
        return ServiceName.LocalNetService;
    }

    @Override
    public void startup() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        gameNettyTcpServerService = new GameNettyTcpServerService(gameServerConfig.getServerId(), gameServerConfig.getPort()
                , GlobalConstants.Thread.NET_TCP_BOSS, GlobalConstants.Thread.NET_TCP_WORKER, new GameNetProtoMessageTcpServerChannleInitializer());
        gameNettyTcpServerService.startService();

        gameNettyUdpServerService = new GameNettyUdpServerService(gameServerConfig.getServerId(),gameServerConfig.getUdpPort()
                , GlobalConstants.Thread.NET_UDP_WORKER);
        gameNettyUdpServerService.startService();

        if(gameServerConfig.isRpcFlag()) {
            gameNettyRPCService = new GameNettyRPCService(gameServerConfig.getServerId(), gameServerConfig.getFirstRpcPort()
                    , GlobalConstants.Thread.NET_RPC_BOSS, GlobalConstants.Thread.NET_RPC_WORKER, new GameNetRPCChannleInitializer());
            gameNettyRPCService.startService();
        }

    }

    @Override
    public void shutdown() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();

        if(gameNettyTcpServerService != null){
            gameNettyTcpServerService.stopService();
        }

        if(gameNettyUdpServerService != null){
            gameNettyUdpServerService.stopService();
        }

        if(gameServerConfig.isRpcFlag()) {
            if (gameNettyRPCService != null) {
                gameNettyRPCService.stopService();
            }
        }
    }

    public GameNettyTcpServerService getGameNettyTcpServerService() {
        return gameNettyTcpServerService;
    }

    public void setGameNettyTcpServerService(GameNettyTcpServerService gameNettyTcpServerService) {
        this.gameNettyTcpServerService = gameNettyTcpServerService;
    }

    public GameNettyUdpServerService getGameNettyUdpServerService() {
        return gameNettyUdpServerService;
    }

    public void setGameNettyUdpServerService(GameNettyUdpServerService gameNettyUdpServerService) {
        this.gameNettyUdpServerService = gameNettyUdpServerService;
    }

    public GameNettyRPCService getGameNettyRPCService() {
        return gameNettyRPCService;
    }

    public void setGameNettyRPCService(GameNettyRPCService gameNettyRPCService) {
        this.gameNettyRPCService = gameNettyRPCService;
    }
}
