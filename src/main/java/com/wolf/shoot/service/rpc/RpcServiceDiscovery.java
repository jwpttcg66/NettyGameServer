package com.wolf.shoot.service.rpc;

import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.BOEnum;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.common.util.FileUtil;
import com.wolf.shoot.common.util.JdomUtils;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.rpc.client.ConnectManager;
import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.slf4j.Logger;
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

    public void updateConnectedServer(List<SdServer> sdServers) throws InterruptedException {
        ConnectManager.getInstance().initServers(sdServers);
    }

    public void updateOnlineConnectedServer() throws InterruptedException {
        this.updateConnectedServer(sdWorldServers);
    }

    @Override
    public String getId() {
        return ServiceName.RpcServiceDiscovery;
    }

    @Override
    public void startup() throws Exception {
        init();
//        updateConnectedServer();
    }

    @Override
    public void shutdown() throws Exception {
        ConnectManager.getInstance().stop();
    }

    @SuppressWarnings("unchecked")
    public void init() throws DataConversionException {

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
    }


}

