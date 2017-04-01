package com.wolf.shoot.manager.spring;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.loader.DefaultClassLoader;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.cache.EhcacheService;
import com.wolf.shoot.service.lookup.GamePlayerLoopUpService;
import com.wolf.shoot.service.lookup.NetTcpSessionLoopUpService;
import com.wolf.shoot.service.net.message.facade.GameFacade;
import com.wolf.shoot.service.net.message.registry.MessageRegistry;
import com.wolf.shoot.service.rpc.client.DetectRPCPendingService;
import com.wolf.shoot.service.rpc.server.RemoteRpcHandlerService;
import com.wolf.shoot.service.rpc.server.RpcMethodRegistry;
import com.wolf.shoot.service.rpc.client.RpcServiceDiscovery;
import com.wolf.shoot.service.rpc.client.RpcSenderProxy;
import com.wolf.shoot.service.time.SystemTimeService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;

/**
 * Created by jiangwenping on 17/3/1.
 * 本地spring会话服务
 */
@Repository
public class LocalSpringServiceManager {
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
    private RpcServiceDiscovery rpcServiceDiscovery;

    @Autowired
    private EhcacheService ehcacheService;

    @Autowired
    private RpcSenderProxy rpcSenderProxy;
    
    @Autowired
    private DetectRPCPendingService detectRPCPendingService;

    public DetectRPCPendingService getDetectRPCPendingService() {
		return detectRPCPendingService;
	}

	public void setDetectRPCPendingService(
			DetectRPCPendingService detectRPCPendingService) {
		this.detectRPCPendingService = detectRPCPendingService;
	}

	public RpcServiceDiscovery getRpcServiceDiscovery() {
        return rpcServiceDiscovery;
    }

    public void setRpcServiceDiscovery(RpcServiceDiscovery rpcServiceDiscovery) {
        this.rpcServiceDiscovery = rpcServiceDiscovery;
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

    public EhcacheService getEhcacheService() {
        return ehcacheService;
    }

    public void setEhcacheService(EhcacheService ehcacheService) {
        this.ehcacheService = ehcacheService;
    }

    public RpcSenderProxy getRpcSenderProxy() {
        return rpcSenderProxy;
    }

    public void setRpcSenderProxy(RpcSenderProxy rpcSenderProxy) {
        this.rpcSenderProxy = rpcSenderProxy;
    }

    public  void start() throws Exception {

//        this.defaultClassLoader.startup();
//        this.gameServerConfigService.startup();
//        this.messageRegistry.startup();
//        this.gameFacade.startup();
//        this.rpcMethodRegistry.startup();
//        this.remoteRpcService.startup();
//        this.rpcServiceDiscovery.startup();
//        this.ehcacheService.startup();
        // 获取对象obj的所有属性域
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            // 对于每个属性，获取属性名
            String varName = field.getName();
            try {
                boolean access = field.isAccessible();
                if (!access) field.setAccessible(true);

                //从obj中获取field变量
                Object object = field.get(this);
                if(object instanceof IService){
                    IService iService = (IService) object;
                    iService.startup();
                    logger.info(iService.getId() + " service start up");
                }else{
                    logger.info(object.getClass().getSimpleName() + " start up");
                }
                if (!access) field.setAccessible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public void stop() throws Exception{
//        this.defaultClassLoader.shutdown();
//        this.getGameServerConfigService().shutdown();
//        this.messageRegistry.shutdown();
//        this.gameFacade.shutdown();
//        this.rpcMethodRegistry.shutdown();
//        this.remoteRpcService.shutdown();
//        this.rpcServiceDiscovery.shutdown();
//        this.ehcacheService.shutdown();

        // 获取对象obj的所有属性域
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            // 对于每个属性，获取属性名
            String varName = field.getName();
            try {
                boolean access = field.isAccessible();
                if (!access) field.setAccessible(true);

                //从obj中获取field变量
                Object object = field.get(this);
                if(object instanceof IService){
                    IService iService = (IService) object;
                    iService.shutdown();
                    logger.info(iService.getId() + "shut down");
                }else{
                    logger.info(object.getClass().getSimpleName() + " shut down");
                }
                if (!access) field.setAccessible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }
}
