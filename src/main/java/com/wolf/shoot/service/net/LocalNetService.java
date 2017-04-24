package com.wolf.shoot.service.net;

import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.common.exception.StartUpException;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.rpc.server.RpcConfig;
import com.wolf.shoot.service.rpc.server.SdRpcServiceProvider;

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
        //根据rpc配置来决定启动服务器
        RpcConfig rpcConfig = gameServerConfigService.getRpcConfig();
        SdRpcServiceProvider sdRpcServiceProvider = rpcConfig.getSdRpcServiceProvider();

        gameNettyTcpServerService = new GameNettyTcpServerService(gameServerConfig.getServerId(), gameServerConfig.getPort()
                , GlobalConstants.Thread.NET_TCP_BOSS, GlobalConstants.Thread.NET_TCP_WORKER, new GameNetProtoMessageTcpServerChannleInitializer());
        boolean startUpFlag = gameNettyTcpServerService.startService();
        if(!startUpFlag){
            throw  new StartUpException("tcp server startup error");
        }

        if(gameServerConfig.isUdpOpen()) {
            gameNettyUdpServerService = new GameNettyUdpServerService(gameServerConfig.getServerId(), gameServerConfig.getUdpPort()
                    , GlobalConstants.Thread.NET_UDP_WORKER);
            startUpFlag = gameNettyUdpServerService.startService();
            if (!startUpFlag) {
                throw new StartUpException("udp server startup error");
            }
        }

        if(gameServerConfig.isRpcOpen()) {
            gameNettyRPCService = new GameNettyRPCService(gameServerConfig.getServerId(), gameServerConfig.getFirstRpcPort()
                    , GlobalConstants.Thread.NET_RPC_BOSS, GlobalConstants.Thread.NET_RPC_WORKER, new GameNetRPCChannleInitializer());
            startUpFlag = gameNettyRPCService.startService();
            if(!startUpFlag){
                throw  new StartUpException("rpc server startup error");
            }

        }

    }

    @Override
    public void shutdown() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();

        if(gameNettyTcpServerService != null){
            gameNettyTcpServerService.stopService();
        }

        if(gameServerConfig.isUdpOpen()) {
            if (gameNettyUdpServerService != null) {
                gameNettyUdpServerService.stopService();
            }
        }

        if(gameServerConfig.isRpcOpen()) {
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
