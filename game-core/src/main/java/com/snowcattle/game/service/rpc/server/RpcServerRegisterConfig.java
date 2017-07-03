package com.snowcattle.game.service.rpc.server;

import com.snowcattle.game.common.enums.BOEnum;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.util.FileUtil;
import com.snowcattle.game.common.util.JdomUtils;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangwenping on 17/4/1.
 */
@Service
public class RpcServerRegisterConfig {

    private static final Logger LOGGER = Loggers.rpcLogger;

    protected Object lock = new Object();

    protected List<SdServer> sdWorldServers;
    protected List<SdServer> sdGameServers;
    protected List<SdServer> sdDbServers;

    private SdRpcServiceProvider sdRpcServiceProvider;

    @SuppressWarnings("unchecked")
    public void init() throws Exception {

        Element rootElement = JdomUtils.getRootElemet(FileUtil.getConfigURL(GlobalConstants.ConfigFile.RPC_SERVER_REGISTER_CONFIG).getFile());

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

        SdRpcServiceProvider sdRpcServiceProvider = new SdRpcServiceProvider();
        rootElement = JdomUtils.getRootElemet(FileUtil.getConfigURL(GlobalConstants.ConfigFile.RPC_SERVEICE_CONFIG).getFile());
        childrenElements = rootElement.getChildren("service");
        for (Element childElement : childrenElements) {
            sdRpcServiceProvider.load(childElement);
        }

        synchronized (this.lock) {
            this.sdRpcServiceProvider = sdRpcServiceProvider;
        }
    }

    public SdRpcServiceProvider getSdRpcServiceProvider() {
        return sdRpcServiceProvider;
    }

    public void setSdRpcServiceProvider(SdRpcServiceProvider sdRpcServiceProvider) {
        this.sdRpcServiceProvider = sdRpcServiceProvider;
    }

    public List<SdServer> getSdWorldServers() {
        return sdWorldServers;
    }

    public void setSdWorldServers(List<SdServer> sdWorldServers) {
        this.sdWorldServers = sdWorldServers;
    }

    public List<SdServer> getSdGameServers() {
        return sdGameServers;
    }

    public void setSdGameServers(List<SdServer> sdGameServers) {
        this.sdGameServers = sdGameServers;
    }

    public List<SdServer> getSdDbServers() {
        return sdDbServers;
    }

    public void setSdDbServers(List<SdServer> sdDbServers) {
        this.sdDbServers = sdDbServers;
    }

    public boolean validServer(int boId){
        return sdRpcServiceProvider.validServer(boId);
    }
}
