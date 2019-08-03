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
import com.snowcattle.game.service.net.websocket.GameNetProtoMessageWebSocketServerChannelInitializer;
import com.snowcattle.game.service.net.websocket.GameNettyWebSocketServerService;
import com.snowcattle.game.service.net.websocket.NetWebSocketServerConfig;
import com.snowcattle.game.service.net.websocket.SdWebSocketServerConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * Created by jiangwenping on 17/2/15.
 * 本地网络服务
 */
public class LocalNetService implements IService{

    private final Logger serverLogger = Loggers.serverLogger;

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

    /**
     * websocket服务
     * */
    private GameNettyWebSocketServerService gameNettyWebSocketServerService;

    private SslContext sslContext;

    private ChannelInitializer<NioSocketChannel> nettyTcpChannelInitializer;
    private ChannelInitializer<NioDatagramChannel> nettyUdpChannelInitializer;
    private ChannelInitializer<NioSocketChannel> rpcChannelInitializer;
    private ChannelInitializer<NioSocketChannel> proxyChannleInitializer;
    private ChannelInitializer<SocketChannel>  httpChannelInitialier;
    private ChannelInitializer<SocketChannel> webSocketChannelInitialer;

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
        if(netHttpServerConfig != null) {
            SdHttpServerConfig sdHttpServerConfig = netHttpServerConfig.getSdHttpServerConfig();
            if (sdHttpServerConfig != null) {
                gameNettyHttpServerService = new GameNettyHttpServerService(sdHttpServerConfig.getId(), sdHttpServerConfig.getPort()
                        , GlobalConstants.Thread.NET_HTTP_BOSS, GlobalConstants.Thread.NET_HTTP_WORKER, httpChannelInitialier);
                startUpFlag = gameNettyHttpServerService.startService();
                if (!startUpFlag) {
                    throw new StartUpException("http server startup error");
                }
                serverLogger.info("gameNettyHttpServerService start " + startUpFlag + " port " + sdHttpServerConfig.getPort());
            }
        }
        NetWebSocketServerConfig netWebSocketServerConfig = gameServerConfigService.getNetWebSocketServerConfig();
        if(netWebSocketServerConfig  != null){
            SdWebSocketServerConfig sdWebSocketServerConfig = netWebSocketServerConfig.getSdWebSocketServerConfig();
            if(sdWebSocketServerConfig != null) {
                gameNettyWebSocketServerService = new GameNettyWebSocketServerService(sdWebSocketServerConfig.getId(), sdWebSocketServerConfig.getPort()
                        , GlobalConstants.Thread.NET_WEB_SOCKET_BOSS, GlobalConstants.Thread.NET_WEB_SOCKET_WORKER, webSocketChannelInitialer);

                serverLogger.info("gameNettyWebSocketService start " + startUpFlag + " port " + sdWebSocketServerConfig.getPort());
                startUpFlag = gameNettyWebSocketServerService.startService();
                if (!startUpFlag) {
                    throw new StartUpException("web socket server startup error");
                }
            }
            serverLogger.info("gameNettyWebSocketServerService start " + startUpFlag + " port " + sdWebSocketServerConfig.getPort());
        }
    }

    public void initChannelInitializer() throws CertificateException, SSLException {

        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        NetWebSocketServerConfig netWebSocketServerConfig = gameServerConfigService.getNetWebSocketServerConfig();
        if(netWebSocketServerConfig  != null){
            SdWebSocketServerConfig sdWebSocketServerConfig = netWebSocketServerConfig.getSdWebSocketServerConfig();
            if(sdWebSocketServerConfig != null) {
                boolean sslFlag  = sdWebSocketServerConfig.isSsl();
                if(sslFlag) {
                    SelfSignedCertificate ssc = new SelfSignedCertificate();
                    sslContext = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
                }

            }
        }

        nettyTcpChannelInitializer = new GameNetProtoMessageTcpServerChannelInitializer();
        nettyUdpChannelInitializer = new GameNetProtoMessageUdpServerChannelInitializer();
        rpcChannelInitializer = new GameNetRPCChannleInitializer();
        proxyChannleInitializer = new ProxyTcpFrontedChannelInitializer();
        httpChannelInitialier = new GameNetProtoMessageHttpServerChannelInitializer();
        webSocketChannelInitialer = new GameNetProtoMessageWebSocketServerChannelInitializer(sslContext);
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

        NetWebSocketServerConfig netWebSocketServerConfig = gameServerConfigService.getNetWebSocketServerConfig();
        SdWebSocketServerConfig sdWebSocketServerConfig = netWebSocketServerConfig.getSdWebSocketServerConfig();
        if(sdWebSocketServerConfig != null){
            if(gameNettyWebSocketServerService != null){
                gameNettyWebSocketServerService.stopService();
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
