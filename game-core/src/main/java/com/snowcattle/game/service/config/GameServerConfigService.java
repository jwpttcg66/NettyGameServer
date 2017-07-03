package com.snowcattle.game.service.config;

import com.snowcattle.game.common.config.*;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.service.net.http.NetHttpServerConfig;
import com.snowcattle.game.service.net.proxy.NetProxyConfig;
import com.snowcattle.game.service.net.udp.NetUdpServerConfig;
import com.snowcattle.game.service.rpc.server.RpcServerRegisterConfig;
import com.snowcattle.game.service.IService;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.net.URL;


/**
 * 游戏配置服务
 */
@Service
public class GameServerConfigService implements IService {

    protected GameServerDiffConfig gameServerDiffConfig;
    protected GameServerConfig gameServerConfig;
    protected GameDynamicPropertiesConfig gameDynamicPropertiesConfig;
    protected ZooKeeperConfig zooKeeperConfig;
    protected RpcServerRegisterConfig rpcServerRegisterConfig;
    protected NetProxyConfig netProxyConfig;
    protected NetUdpServerConfig netUdpServerConfig;
    protected NetHttpServerConfig netHttpServerConfig;


    @Override
    public String getId() {
        return ServiceName.GameServerConfigServiceString;
    }

    @Override
    public void startup() throws Exception {
        init();
    }

    public void init() throws Exception {
        initConfig();
        initDiffConfig();
        initDynamicConfig();
        initRpcConfig();
        initZooKeeperConfig();
        initNetProxyConfig();
        initNetUdpServerConfig();
        initNetHttpServerConfig();
    }

    public void initNetHttpServerConfig() throws  Exception{
        NetHttpServerConfig netHttpServerConfig = new NetHttpServerConfig();
        netHttpServerConfig.init();
        this.netHttpServerConfig = netHttpServerConfig;
    }

    public void initNetUdpServerConfig() throws Exception{
        NetUdpServerConfig netUdpServerConfig = new NetUdpServerConfig();
        netUdpServerConfig.init();
        this.netUdpServerConfig = netUdpServerConfig;
    }

    public void initNetProxyConfig() throws Exception{
        NetProxyConfig netProxyConfig = new NetProxyConfig();
        netProxyConfig.init();
        this.netProxyConfig = netProxyConfig;
    }

    public void initRpcConfig() throws Exception {
        RpcServerRegisterConfig rpcServerRegisterConfig = new RpcServerRegisterConfig();
        rpcServerRegisterConfig.init();
        this.rpcServerRegisterConfig = rpcServerRegisterConfig;
    }

    public void initZooKeeperConfig(){
        DefaultResourceLoader defaultClassLoader = new DefaultResourceLoader();
        Resource resource = defaultClassLoader.getResource(GlobalConstants.ConfigFile.ZOOKEEPER_CONFIG);
        ZooKeeperConfig zooKeeperConfig = new ZooKeeperConfig();
        zooKeeperConfig.setResource(resource);
        zooKeeperConfig.init();
        this.zooKeeperConfig = zooKeeperConfig;
    }
    public void initDynamicConfig(){
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource(GlobalConstants.ConfigFile.DYNAMIC_CONFIG);
        GameDynamicPropertiesConfig tempA5GameDynamicPropertiesConfig = new GameDynamicPropertiesConfig();
        tempA5GameDynamicPropertiesConfig.setResource(resource);
        tempA5GameDynamicPropertiesConfig.init();
        this.gameDynamicPropertiesConfig = tempA5GameDynamicPropertiesConfig;
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

    protected void initConfig(){
         String cfgPath = GlobalConstants.ConfigFile.GAME_SERVER_CONIFG;
         ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
         URL url = classLoader.getResource(cfgPath);
         GameServerConfig  gameServerConfig = ConfigUtil.buildConfig(GameServerConfig.class, url);
         this.gameServerConfig = gameServerConfig;
//         LocalMananger.getInstance().add(this.gameServerConfig, GameServerConfig.class);
    }

    private void initDiffConfig(){
        String cfgPath = GlobalConstants.ConfigFile.GAME_SERVER_DIFF_CONIFG;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(cfgPath);
        GameServerDiffConfig gameServerDiffConfig = ConfigUtil.buildConfig(GameServerDiffConfig.class, url);
        this.gameServerDiffConfig = gameServerDiffConfig;
//        LocalMananger.getInstance().add(this.gameServerDiffConfig, GameServerDiffConfig.class);
    }

    public GameDynamicPropertiesConfig getGameDynamicPropertiesConfig() {
        return gameDynamicPropertiesConfig;
    }

    public void setGameDynamicPropertiesConfig(GameDynamicPropertiesConfig gameDynamicPropertiesConfig) {
        this.gameDynamicPropertiesConfig = gameDynamicPropertiesConfig;
    }

    public ZooKeeperConfig getZooKeeperConfig() {
        return zooKeeperConfig;
    }

    public void setZooKeeperConfig(ZooKeeperConfig zooKeeperConfig) {
        this.zooKeeperConfig = zooKeeperConfig;
    }

    public RpcServerRegisterConfig getRpcServerRegisterConfig() {
        return rpcServerRegisterConfig;
    }

    public void setRpcServerRegisterConfig(RpcServerRegisterConfig rpcServerRegisterConfig) {
        this.rpcServerRegisterConfig = rpcServerRegisterConfig;
    }

    public void setGameServerDiffConfig(GameServerDiffConfig gameServerDiffConfig) {
        this.gameServerDiffConfig = gameServerDiffConfig;
    }

    public void setGameServerConfig(GameServerConfig gameServerConfig) {
        this.gameServerConfig = gameServerConfig;
    }

    public NetProxyConfig getNetProxyConfig() {
        return netProxyConfig;
    }

    public void setNetProxyConfig(NetProxyConfig netProxyConfig) {
        this.netProxyConfig = netProxyConfig;
    }

    public NetUdpServerConfig getNetUdpServerConfig() {
        return netUdpServerConfig;
    }

    public void setNetUdpServerConfig(NetUdpServerConfig netUdpServerConfig) {
        this.netUdpServerConfig = netUdpServerConfig;
    }

    public NetHttpServerConfig getNetHttpServerConfig() {
        return netHttpServerConfig;
    }

    public void setNetHttpServerConfig(NetHttpServerConfig netHttpServerConfig) {
        this.netHttpServerConfig = netHttpServerConfig;
    }
}
