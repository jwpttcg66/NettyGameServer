package com.wolf.shoot.service.rpc.client;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.config.GameServerDiffConfig;
import com.wolf.shoot.common.constant.BOEnum;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.rpc.client.impl.DbRpcConnnectMananger;
import com.wolf.shoot.service.rpc.client.impl.GameRpcConnecetMananger;
import com.wolf.shoot.service.rpc.client.impl.WorldRpcConnectManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/3/8.
 * rpc的服务发现
 */
@Service
public class RpcClientConnectService implements IService {

    private static final Logger LOGGER = Loggers.rpcLogger;

    protected Object lock = new Object();


    @Autowired
    private WorldRpcConnectManager worldRpcConnectManager;

    @Autowired
    private GameRpcConnecetMananger gameRpcConnecetMananger;

    @Autowired
    private DbRpcConnnectMananger dbRpcConnnectMananger;

    public void initWorldConnectedServer() throws Exception {
        worldRpcConnectManager.initManager();
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        worldRpcConnectManager.initServers(gameServerConfigService.getRpcConfig().getSdWorldServers());
    }

    public void initGameConnectedServer() throws Exception {
        gameRpcConnecetMananger.initManager();
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        gameRpcConnecetMananger.initServers(gameServerConfigService.getRpcConfig().getSdGameServers());
    }

    public void initDbConnectServer() throws Exception{
        dbRpcConnnectMananger.initManager();
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        dbRpcConnnectMananger.initServers(gameServerConfigService.getRpcConfig().getSdDbServers());
    }

    @Override
    public String getId() {
        return ServiceName.RpcServiceDiscovery;
    }

    @Override
    public void startup() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerDiffConfig gameServerDiffConfig = gameServerConfigService.getGameServerDiffConfig();
        if(!gameServerDiffConfig.isZookeeperFlag()) {
            init();
        }else{

        }
    }

    @Override
    public void shutdown() throws Exception {
        worldRpcConnectManager.stop();
        gameRpcConnecetMananger.stop();
        dbRpcConnnectMananger.stop();
    }

    @SuppressWarnings("unchecked")
    public void init() throws Exception {
        initWorldConnectedServer();
        initGameConnectedServer();
        initDbConnectServer();
    }


    public AbstractRpcConnectManager getRpcConnectMannger(BOEnum boEnum){
        AbstractRpcConnectManager abstractRpcConnectManager = worldRpcConnectManager;
        if(boEnum.equals(BOEnum.GAME)){
            abstractRpcConnectManager = gameRpcConnecetMananger;
        }else if (boEnum.equals(BOEnum.DB)){
            abstractRpcConnectManager = dbRpcConnnectMananger;
        }
        return worldRpcConnectManager;
    }

}

