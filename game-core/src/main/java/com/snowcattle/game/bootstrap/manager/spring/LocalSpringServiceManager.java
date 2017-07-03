package com.snowcattle.game.bootstrap.manager.spring;

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.service.classes.loader.DefaultClassLoader;
import com.snowcattle.game.service.async.pool.AsyncThreadService;
import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.dict.DictService;
import com.snowcattle.game.service.event.GameAsyncEventService;
import com.snowcattle.game.service.lookup.GamePlayerLoopUpService;
import com.snowcattle.game.service.lookup.NetTcpSessionLoopUpService;
import com.snowcattle.game.service.net.http.handler.async.AsyncNettyHttpHandlerService;
import com.snowcattle.game.service.net.tcp.handler.async.AsyncNettyTcpHandlerService;
import com.snowcattle.game.service.message.facade.GameFacade;
import com.snowcattle.game.service.message.registry.MessageRegistry;
import com.snowcattle.game.service.net.ssl.SSLService;
import com.snowcattle.game.service.rpc.client.RPCFutureService;
import com.snowcattle.game.service.rpc.client.RpcProxyService;
import com.snowcattle.game.service.rpc.server.RemoteRpcHandlerService;
import com.snowcattle.game.service.rpc.server.RpcMethodRegistry;
import com.snowcattle.game.service.rpc.server.zookeeper.ZookeeperRpcServiceRegistry;
import com.snowcattle.game.service.time.SystemTimeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jiangwenping on 17/3/1.
 * 本地spring会话服务
 */
@Repository
public class LocalSpringServiceManager extends AbstractSpringStart{
    private Logger logger = Loggers.serverLogger;

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
    private RemoteRpcHandlerService remoteRpcHandlerService;

    @Autowired
    private RpcProxyService rpcProxyService;
    
    @Autowired
    private RPCFutureService RPCFutureService;

    @Autowired
    private ZookeeperRpcServiceRegistry zookeeperRpcServiceRegistry;

    @Autowired
    private AsyncThreadService asyncThreadService;

    @Autowired
    private DictService dictService;

    @Autowired
    private AsyncNettyTcpHandlerService asyncNettyTcpHandlerService;

    @Autowired
    private GameAsyncEventService gameAsyncEventService;

    @Autowired
    private SSLService sslService;

    @Autowired
    private AsyncNettyHttpHandlerService asyncNettyHttpHandlerService;

    public RPCFutureService getRPCFutureService() {
		return RPCFutureService;
	}

	public void setRPCFutureService(
            RPCFutureService RPCFutureService) {
		this.RPCFutureService = RPCFutureService;
	}

    public RemoteRpcHandlerService getRemoteRpcHandlerService() {
        return remoteRpcHandlerService;
    }

    public void setRemoteRpcHandlerService(RemoteRpcHandlerService remoteRpcHandlerService) {
        this.remoteRpcHandlerService = remoteRpcHandlerService;
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

    public RpcProxyService getRpcProxyService() {
        return rpcProxyService;
    }

    public void setRpcProxyService(RpcProxyService rpcProxyService) {
        this.rpcProxyService = rpcProxyService;
    }


    public ZookeeperRpcServiceRegistry getZookeeperRpcServiceRegistry() {
        return zookeeperRpcServiceRegistry;
    }

    public void setZookeeperRpcServiceRegistry(ZookeeperRpcServiceRegistry zookeeperRpcServiceRegistry) {
        this.zookeeperRpcServiceRegistry = zookeeperRpcServiceRegistry;
    }

    public AsyncThreadService getAsyncThreadService() {
        return asyncThreadService;
    }

    public void setAsyncThreadService(AsyncThreadService asyncThreadService) {
        this.asyncThreadService = asyncThreadService;
    }

    public DictService getDictService() {
        return dictService;
    }

    public void setDictService(DictService dictService) {
        this.dictService = dictService;
    }

    public AsyncNettyTcpHandlerService getAsyncNettyTcpHandlerService() {
        return asyncNettyTcpHandlerService;
    }

    public void setAsyncNettyTcpHandlerService(AsyncNettyTcpHandlerService asyncNettyTcpHandlerService) {
        this.asyncNettyTcpHandlerService = asyncNettyTcpHandlerService;
    }

    public GameAsyncEventService getGameAsyncEventService() {
        return gameAsyncEventService;
    }

    public void setGameAsyncEventService(GameAsyncEventService gameAsyncEventService) {
        this.gameAsyncEventService = gameAsyncEventService;
    }

    public SSLService getSslService() {
        return sslService;
    }

    public void setSslService(SSLService sslService) {
        this.sslService = sslService;
    }

    public AsyncNettyHttpHandlerService getAsyncNettyHttpHandlerService() {
        return asyncNettyHttpHandlerService;
    }

    public void setAsyncNettyHttpHandlerService(AsyncNettyHttpHandlerService asyncNettyHttpHandlerService) {
        this.asyncNettyHttpHandlerService = asyncNettyHttpHandlerService;
    }
}
