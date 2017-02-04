package com.wolf.shoot.config;

import com.wolf.shoot.constant.ServiceName;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;

import javax.xml.ws.Service;
import java.net.URL;

import java.net.URL;


public class GameServerConfigService implements IService {


    private GameServerDiffConfig gameServerDiffConfig;
    private GameServerConfig gameServerConfig;

    @Override
    public String getId() {
        return ServiceName.GameServerConfigServiceString;
    }

    @Override
    public void startup() throws Exception {
        init();
    }

    public void init(){
        String cfgPath = GlobalConstants.ConfigFile.GAME_SERVER_DIFF_CONIFG;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(cfgPath);
        GameServerDiffConfig gameServerDiffConfig = ConfigUtil.buildConfig(GameServerDiffConfig.class, url);
        this.gameServerDiffConfig = gameServerDiffConfig;
        LocalMananger.getInstance().add(this.gameServerDiffConfig, GameServerDiffConfig.class);
        this.gameServerConfig = LocalMananger.getInstance().get(GameServerConfig.class);
    }

    @Override
    public void shutdown() throws Exception {

    }

    public GameServerDiffConfig getGameServerDiffConfig() {
        return gameServerDiffConfig;
    }

    public GameServerConfig getGameServerConfig() {
        return gameServerConfig;
    }
}
