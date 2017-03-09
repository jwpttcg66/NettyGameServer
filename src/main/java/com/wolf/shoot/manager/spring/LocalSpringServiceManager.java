package com.wolf.shoot.manager.spring;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.loader.DefaultClassLoader;
import com.wolf.shoot.service.cache.EhcacheService;
import com.wolf.shoot.service.lookup.GamePlayerLoopUpService;
import com.wolf.shoot.service.lookup.NetTcpSessionLoopUpService;
import com.wolf.shoot.service.net.message.facade.GameFacade;
import com.wolf.shoot.service.net.message.registry.MessageRegistry;
import com.wolf.shoot.service.rpc.RemoteRpcService;
import com.wolf.shoot.service.rpc.RpcMethodRegistry;
import com.wolf.shoot.service.rpc.RpcServiceDiscovery;
import com.wolf.shoot.service.time.SystemTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jiangwenping on 17/3/1.
 * 本地spring会话服务
 */
@Repository
public class LocalSpringServiceManager {

    @Autowired
    private DefaultClassLoader defaultClassLoader;

    @Autowired
    private NetTcpSessionLoopUpService netTcpSessionLoopUpService;

    @Autowired
    private GamePlayerLoopUpService gamePlayerLoopUpService;

    @Autowired
    private GameServerConfigService gameServerConfigService;

    @Autowired
    private MessageRegistry messageRegistry;

    @Autowired
    private GameFacade gameFacade;

    @Autowired
    private SystemTimeService systemTimeService;

    @Autowired
    private RpcMethodRegistry rpcMethodRegistry;

    @Autowired
    private RemoteRpcService remoteRpcService;

    @Autowired
    private RpcServiceDiscovery rpcServiceDiscovery;

    @Autowired
    private EhcacheService ehcacheService;

    public RpcServiceDiscovery getRpcServiceDiscovery() {
        return rpcServiceDiscovery;
    }

    public void setRpcServiceDiscovery(RpcServiceDiscovery rpcServiceDiscovery) {
        this.rpcServiceDiscovery = rpcServiceDiscovery;
    }

    public RemoteRpcService getRemoteRpcService() {
        return remoteRpcService;
    }

    public void setRemoteRpcService(RemoteRpcService remoteRpcService) {
        this.remoteRpcService = remoteRpcService;
    }

    public RpcMethodRegistry getRpcMethodRegistry() {
        return rpcMethodRegistry;
    }

    public void setRpcMethodRegistry(RpcMethodRegistry rpcMethodRegistry) {
        this.rpcMethodRegistry = rpcMethodRegistry;
    }

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

    public GamePlayerLoopUpService getGamePlayerLoopUpService() {
        return gamePlayerLoopUpService;
    }

    public void setGamePlayerLoopUpService(GamePlayerLoopUpService gamePlayerLoopUpService) {
        this.gamePlayerLoopUpService = gamePlayerLoopUpService;
    }

    public MessageRegistry getMessageRegistry() {
        return messageRegistry;
    }

    public void setMessageRegistry(MessageRegistry messageRegistry) {
        this.messageRegistry = messageRegistry;
    }

    public GameFacade getGameFacade() {
        return gameFacade;
    }

    public void setGameFacade(GameFacade gameFacade) {
        this.gameFacade = gameFacade;
    }

    public SystemTimeService getSystemTimeService() {
        return systemTimeService;
    }

    public void setSystemTimeService(SystemTimeService systemTimeService) {
        this.systemTimeService = systemTimeService;
    }

    public DefaultClassLoader getDefaultClassLoader() {
        return defaultClassLoader;
    }

    public void setDefaultClassLoader(DefaultClassLoader defaultClassLoader) {
        this.defaultClassLoader = defaultClassLoader;
    }

    public EhcacheService getEhcacheService() {
        return ehcacheService;
    }

    public void setEhcacheService(EhcacheService ehcacheService) {
        this.ehcacheService = ehcacheService;
    }

    public  void start() throws Exception {
        this.defaultClassLoader.startup();
        this.gameServerConfigService.startup();
        this.messageRegistry.startup();
        this.gameFacade.startup();
        this.rpcMethodRegistry.startup();
        this.remoteRpcService.startup();
        this.rpcServiceDiscovery.startup();
        this.ehcacheService.startup();
    }

    public void stop() throws Exception{
        this.defaultClassLoader.shutdown();
        this.getGameServerConfigService().shutdown();
        this.messageRegistry.shutdown();
        this.gameFacade.shutdown();
        this.rpcMethodRegistry.shutdown();
        this.remoteRpcService.shutdown();
        this.rpcServiceDiscovery.shutdown();
        this.ehcacheService.shutdown();
    }
}
