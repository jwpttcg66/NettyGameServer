package com.wolf.shoot.service.rpc.server;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.BOEnum;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.common.util.FileUtil;
import com.wolf.shoot.common.util.JdomUtils;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.rpc.client.RpcConnectManager;
import com.wolf.shoot.service.rpc.client.impl.DbRpcConnnectManngeer;
import com.wolf.shoot.service.rpc.client.impl.GameRpcConnecetMananger;
import com.wolf.shoot.service.rpc.client.impl.WorldRpcConnectManager;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jwp on 2017/3/8.
 * rpc的服务发现
 */
@Service
public class RpcServiceDiscovery implements IService {

    private static final Logger LOGGER = Loggers.rpcLogger;

    protected Object lock = new Object();

    protected List<SdServer> sdWorldServers;
    protected List<SdServer> sdGameServers;
    protected List<SdServer> sdDbServers;

    @Autowired
    private WorldRpcConnectManager worldRpcConnectManager;

    @Autowired
    private GameRpcConnecetMananger gameRpcConnecetMananger;

    @Autowired
    private DbRpcConnnectManngeer dbRpcConnnectManngeer;


    public void initWorldConnectedServer() throws Exception {
        worldRpcConnectManager.initManager();
        worldRpcConnectManager.initServers(sdWorldServers);
    }

    public void initGameConnectedServer() throws Exception {
        worldRpcConnectManager.initManager();
        gameRpcConnecetMananger.initServers(sdGameServers);
    }

    public void initDbConnectServer() throws Exception{
        worldRpcConnectManager.initManager();
        dbRpcConnnectManngeer.initServers(sdDbServers);
    }

    @Override
    public String getId() {
        return ServiceName.RpcServiceDiscovery;
    }

    @Override
    public void startup() throws Exception {
        init();
    }

    @Override
    public void shutdown() throws Exception {
        worldRpcConnectManager.stop();
        gameRpcConnecetMananger.stop();
        dbRpcConnnectManngeer.stop();
    }

    @SuppressWarnings("unchecked")
    public void init() throws Exception {

        GameServerConfigService gameServerConfigServiceEx = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        Element rootElement = JdomUtils.getRootElemet(FileUtil.getConfigURL(GlobalConstants.ConfigFile.RPC_SERVER_CONFIG).getFile());

        Map<Integer, SdServer> serverMap = new HashMap<>();

        List<SdServer> sdWorldServers = new ArrayList<SdServer>();
        Element element = rootElement.getChild(BOEnum.WORLD.toString().toLowerCase());
        List<Element> childrenElements = element.getChildren("server");
        for (Element childElement : childrenElements) {
            SdServer sdServer = new SdServer();
            sdServer.load(childElement);
            sdWorldServers.add(sdServer);
        }

        List<SdServer> sdGameServers = new ArrayList<SdServer>();
        element = rootElement.getChild(BOEnum.GAME.toString().toLowerCase());
        childrenElements = element.getChildren("server");
        for (Element childElement : childrenElements) {
            SdServer sdServer = new SdServer();
            sdServer.load(childElement);
            sdGameServers.add(sdServer);
        }

        List<SdServer> sdDbServers = new ArrayList<SdServer>();
        element = rootElement.getChild(BOEnum.DB.toString().toLowerCase());
        childrenElements = element.getChildren("server");
        for (Element childElement : childrenElements) {
            SdServer sdServer = new SdServer();
            sdServer.load(childElement);
            sdDbServers.add(sdServer);
        }

        synchronized (this.lock) {
            this.sdWorldServers = sdWorldServers;
            this.sdGameServers = sdGameServers;
            this.sdDbServers = sdDbServers;
        }

        initWorldConnectedServer();
        initGameConnectedServer();
        initDbConnectServer();;
    }


    public RpcConnectManager getRpcConnectMannger(BOEnum boEnum){
        RpcConnectManager rpcConnectManager = worldRpcConnectManager;
        if(boEnum.equals(BOEnum.GAME)){
            rpcConnectManager = gameRpcConnecetMananger;
        }else if (boEnum.equals(BOEnum.DB)){
            rpcConnectManager = dbRpcConnnectManngeer;
        }
        return worldRpcConnectManager;
    }
}

