package com.snowcattle.game.service.net;

import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.config.GameServerConfigService;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.common.exception.StartUpException;
import com.snowcattle.game.manager.LocalMananger;
import com.snowcattle.game.service.IService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by jiangwenping on 17/2/15.
 * 本地网络服务
 */
public class LocalNetService implements IService{

    private GameNettyTcpServerService gameNettyTcpServerService;
    private GameNettyUdpServerService gameNettyUdpServerService;
    private GameNettyRPCService gameNettyRPCService;

    private ChannelInitializer<NioSocketChannel> nettyTcpChannelInitializer = new GameNetProtoMessageTcpServerChannleInitializer();
    private ChannelInitializer<NioDatagramChannel> nettyUdpChannelInitializer = new GameNetProtoMessageUdpServerChannleInitializer();

    @Override
    public String getId() {
        return ServiceName.LocalNetService;
    }

    @Override
    public void startup() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        //根据rpc配置来决定启动服务器
//        RpcConfig rpcConfig = gameServerConfigService.getRpcConfig();
//        SdRpcServiceProvider sdRpcServiceProvider = rpcConfig.getSdRpcServiceProvider();

        gameNettyTcpServerService = new GameNettyTcpServerService(gameServerConfig.getServerId(), gameServerConfig.getPort()
                , GlobalConstants.Thread.NET_TCP_BOSS, GlobalConstants.Thread.NET_TCP_WORKER, nettyTcpChannelInitializer);
        boolean startUpFlag = gameNettyTcpServerService.startService();
        if(!startUpFlag){
            throw  new StartUpException("tcp server startup error");
        }

        if(gameServerConfig.isUdpOpen()) {
            gameNettyUdpServerService = new GameNettyUdpServerService(gameServerConfig.getServerId(), gameServerConfig.getUdpPort()
                    , GlobalConstants.Thread.NET_UDP_WORKER, nettyUdpChannelInitializer);
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

    public ChannelInitializer<NioSocketChannel> getNettyTcpChannelInitializer() {
        return nettyTcpChannelInitializer;
    }

    public void setNettyTcpChannelInitializer(ChannelInitializer<NioSocketChannel> nettyTcpChannelInitializer) {
        this.nettyTcpChannelInitializer = nettyTcpChannelInitializer;
    }

    public ChannelInitializer<NioDatagramChannel> getNettyUdpChannelInitializer() {
        return nettyUdpChannelInitializer;
    }

    public void setNettyUdpChannelInitializer(ChannelInitializer<NioDatagramChannel> nettyUdpChannelInitializer) {
        this.nettyUdpChannelInitializer = nettyUdpChannelInitializer;
    }
}
