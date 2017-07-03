package com.snowcattle.game.service.rpc.client;

import com.snowcattle.game.service.config.GameServerConfigService;
import com.snowcattle.game.service.rpc.client.impl.GameRpcConnecetMananger;
import com.snowcattle.game.common.config.GameServerDiffConfig;
import com.snowcattle.game.common.enums.BOEnum;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.IService;
import com.snowcattle.game.service.rpc.client.impl.DbRpcConnnectMananger;
import com.snowcattle.game.service.rpc.client.impl.WorldRpcConnectManager;
import com.snowcattle.game.service.rpc.server.SdServer;
import com.snowcattle.game.service.rpc.server.zookeeper.ZooKeeperNodeBoEnum;
import com.snowcattle.game.service.rpc.server.zookeeper.ZooKeeperNodeInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void initWorldConnectedServer(List<SdServer> sdServerList) throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        worldRpcConnectManager.initServers(sdServerList);
    }

    public void initGameConnectedServer(List<SdServer> sdServerList) throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        gameRpcConnecetMananger.initServers(sdServerList);
    }

    public void initDbConnectServer(List<SdServer> sdServerList) throws Exception{
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        dbRpcConnnectMananger.initServers(sdServerList);
    }

    @Override
    public String getId() {
        return ServiceName.RpcServiceDiscovery;
    }

    @Override
    public void startup() throws Exception {
        worldRpcConnectManager.initManager();
        gameRpcConnecetMananger.initManager();
        dbRpcConnnectMananger.initManager();
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerDiffConfig gameServerDiffConfig = gameServerConfigService.getGameServerDiffConfig();
        if(!gameServerDiffConfig.isZookeeperFlag()) {
            init();
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
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        initWorldConnectedServer(gameServerConfigService.getRpcServerRegisterConfig().getSdWorldServers());
        initGameConnectedServer(gameServerConfigService.getRpcServerRegisterConfig().getSdGameServers());
        initDbConnectServer(gameServerConfigService.getRpcServerRegisterConfig().getSdDbServers());
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

    public AbstractRpcConnectManager getRpcConnectMannger(ZooKeeperNodeBoEnum zooKeeperNodeBoEnu){
        AbstractRpcConnectManager abstractRpcConnectManager = worldRpcConnectManager;
        if(zooKeeperNodeBoEnu.equals(ZooKeeperNodeBoEnum.GAME)){
            abstractRpcConnectManager = gameRpcConnecetMananger;
        }else if (zooKeeperNodeBoEnu.equals(ZooKeeperNodeBoEnum.DB)){
            abstractRpcConnectManager = dbRpcConnnectMananger;
        }
        return worldRpcConnectManager;
    }

    public void notifyConnect(ZooKeeperNodeBoEnum zooKeeperNodeBoEnum, List<ZooKeeperNodeInfo> zooKeeperNodeInfoList) throws InterruptedException {
       getRpcConnectMannger(zooKeeperNodeBoEnum).initZookeeperRpcServers(zooKeeperNodeInfoList);
    }


}

