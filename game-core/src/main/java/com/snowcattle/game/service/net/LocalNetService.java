package com.snowcattle.game.service.net;

import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.common.exception.StartUpException;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.IService;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.net.http.GameNetProtoMessageHttpServerChannelInitializer;
import com.snowcattle.game.service.net.http.GameNettyHttpServerService;
import com.snowcattle.game.service.net.http.NetHttpServerConfig;
import com.snowcattle.game.service.net.http.SdHttpServerConfig;
import com.snowcattle.game.service.net.tcp.*;
import com.snowcattle.game.service.net.udp.GameNetProtoMessageUdpServerChannelInitializer;
import com.snowcattle.game.service.net.udp.GameNettyUdpServerService;
import com.snowcattle.game.service.net.proxy.NetProxyConfig;
import com.snowcattle.game.service.net.proxy.ProxyTcpFrontedChannelInitializer;
import com.snowcattle.game.service.net.proxy.ProxyTcpServerService;
import com.snowcattle.game.service.net.proxy.SdProxyConfig;
import com.snowcattle.game.service.net.udp.NetUdpServerConfig;
import com.snowcattle.game.service.net.udp.SdUdpServerConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;

/**
 * Created by jiangwenping on 17/2/15.
 * 本地网络服务
 */
public class LocalNetService implements IService{

    private Logger serverLogger = Loggers.serverLogger;

    /**
     * tcp服务
     */
    private GameNettyTcpServerService gameNettyTcpServerService;
    /**
     * udp服务
     */
    private GameNettyUdpServerService gameNettyUdpServerService;
    /**
     * rpc的tcp服务
     */
    private GameNettyRPCService gameNettyRPCService;

    /**
     * 代理服务
     */
    private ProxyTcpServerService proxyTcpServerService;
    /**
     * http服务
     */
    private GameNettyHttpServerService gameNettyHttpServerService;

    private ChannelInitializer<NioSocketChannel> nettyTcpChannelInitializer;
    private ChannelInitializer<NioDatagramChannel> nettyUdpChannelInitializer;
    private ChannelInitializer<NioSocketChannel> rpcChannelInitializer;
    private ChannelInitializer<NioSocketChannel> proxyChannleInitializer;
    private ChannelInitializer<SocketChannel>  httpChannelInitialier;

    public LocalNetService() {
    }

    @Override
    public String getId() {
        return ServiceName.LocalNetService;
    }

    @Override
    public void startup() throws Exception {
        initChannelInitializer();
        initNetService();

    }

    public void initNetService() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        gameNettyTcpServerService = new GameNettyTcpServerService(gameServerConfig.getServerId(), gameServerConfig.getPort()
                , GlobalConstants.Thread.NET_TCP_BOSS, GlobalConstants.Thread.NET_TCP_WORKER, nettyTcpChannelInitializer);
        boolean startUpFlag = gameNettyTcpServerService.startService();
        if(!startUpFlag){
            throw  new StartUpException("tcp server startup error");
        }
        serverLogger.info("gameNettyTcpServerService start " + startUpFlag);

        NetUdpServerConfig netUdpServerConfig = gameServerConfigService.getNetUdpServerConfig();
        SdUdpServerConfig sdUdpServerConfig = netUdpServerConfig.getSdUdpServerConfig();
        if(sdUdpServerConfig != null) {
            gameNettyUdpServerService = new GameNettyUdpServerService(sdUdpServerConfig.getId(), sdUdpServerConfig.getPort()
                    , GlobalConstants.Thread.NET_UDP_WORKER, nettyUdpChannelInitializer);
            startUpFlag = gameNettyUdpServerService.startService();
            if (!startUpFlag) {
                throw new StartUpException("udp server startup error");
            }

            serverLogger.info("gameNettyUdpServerService start " + startUpFlag);
        }

        if(gameServerConfig.isRpcOpen()) {
            gameNettyRPCService = new GameNettyRPCService(gameServerConfig.getServerId(), gameServerConfig.getFirstRpcPort()
                    , GlobalConstants.Thread.NET_RPC_BOSS, GlobalConstants.Thread.NET_RPC_WORKER, rpcChannelInitializer);
            startUpFlag = gameNettyRPCService.startService();
            if(!startUpFlag){
                throw  new StartUpException("rpc server startup error");
            }

            serverLogger.info("gameNettyRPCService start " + startUpFlag);
        }

        NetProxyConfig netProxyConfig = gameServerConfigService.getNetProxyConfig();
        SdProxyConfig sdProxyConfig  = netProxyConfig.getSdProxyConfig();
        if(sdProxyConfig != null){
            //启动代理服务
            proxyTcpServerService = new ProxyTcpServerService(sdProxyConfig.getId(), sdProxyConfig.getPort()
                    , GlobalConstants.Thread.NET_PROXY_BOSS, GlobalConstants.Thread.NET_PROXY_WORKER, proxyChannleInitializer);
            startUpFlag = proxyTcpServerService.startService();
            if(!startUpFlag){
                throw  new StartUpException("proxy server startup error");
            }
            serverLogger.info("proxyTcpServerService start " + startUpFlag + " port " + sdProxyConfig.getPort());
        }

        NetHttpServerConfig netHttpServerConfig = gameServerConfigService.getNetHttpServerConfig();
        SdHttpServerConfig sdHttpServerConfig = netHttpServerConfig.getSdHttpServerConfig();
        if(sdHttpServerConfig != null){
            gameNettyHttpServerService = new GameNettyHttpServerService(sdHttpServerConfig.getId(), sdHttpServerConfig.getPort()
                , GlobalConstants.Thread.NET_HTTP_BOSS, GlobalConstants.Thread.NET_HTTP_WORKER, httpChannelInitialier);
            startUpFlag = gameNettyHttpServerService.startService();
            if(!startUpFlag){
                throw  new StartUpException("http server startup error");
            }
            serverLogger.info("gameNettyHttpServerService start " + startUpFlag + " port " + sdHttpServerConfig.getPort());
        }
    }

    public void initChannelInitializer(){
        nettyTcpChannelInitializer = new GameNetProtoMessageTcpServerChannelInitializer();
        nettyUdpChannelInitializer = new GameNetProtoMessageUdpServerChannelInitializer();
        rpcChannelInitializer = new GameNetRPCChannleInitializer();
        proxyChannleInitializer = new ProxyTcpFrontedChannelInitializer();
        httpChannelInitialier = new GameNetProtoMessageHttpServerChannelInitializer();
    }

    @Override
    public void shutdown() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();

        if(gameNettyTcpServerService != null){
            gameNettyTcpServerService.stopService();
        }

        NetUdpServerConfig netUdpServerConfig = gameServerConfigService.getNetUdpServerConfig();
        if(netUdpServerConfig.getSdUdpServerConfig() != null) {
            if (gameNettyUdpServerService != null) {
                gameNettyUdpServerService.stopService();
            }
        }

        if(gameServerConfig.isRpcOpen()) {
            if (gameNettyRPCService != null) {
                gameNettyRPCService.stopService();
            }
        }

        if (proxyTcpServerService != null) {
            proxyTcpServerService.stopService();
        }

        NetHttpServerConfig netHttpServerConfig = gameServerConfigService.getNetHttpServerConfig();
        SdHttpServerConfig sdHttpServerConfig = netHttpServerConfig.getSdHttpServerConfig();
        if(sdHttpServerConfig != null){
            if(gameNettyHttpServerService != null){
                gameNettyHttpServerService.stopService();
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
