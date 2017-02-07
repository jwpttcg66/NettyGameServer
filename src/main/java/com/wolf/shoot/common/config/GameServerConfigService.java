package com.wolf.shoot.common.config;

import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;

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
        initConfig();
        initDiffConfig();
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

     private void initConfig(){
         String cfgPath = GlobalConstants.ConfigFile.GAME_SERVER_CONIFG;
         ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
         URL url = classLoader.getResource(cfgPath);
         GameServerConfig  gameServerConfig = ConfigUtil.buildConfig(GameServerConfig.class, url);
         this.gameServerConfig = gameServerConfig;
         LocalMananger.getInstance().add(this.gameServerConfig, GameServerConfig.class);
    }

    private void initDiffConfig(){
        String cfgPath = GlobalConstants.ConfigFile.GAME_SERVER_DIFF_CONIFG;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(cfgPath);
        GameServerDiffConfig gameServerDiffConfig = ConfigUtil.buildConfig(GameServerDiffConfig.class, url);
        this.gameServerDiffConfig = gameServerDiffConfig;
        LocalMananger.getInstance().add(this.gameServerDiffConfig, GameServerDiffConfig.class);
    }

}
