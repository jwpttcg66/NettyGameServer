package com.snowcattle.game.bootstrap.manager;

/**
 * Created by jwp on 2017/2/4.
 */

import com.snowcattle.game.executor.update.service.UpdateService;
import com.snowcattle.game.bootstrap.manager.spring.LocalSpringBeanManager;
import com.snowcattle.game.bootstrap.manager.spring.LocalSpringServiceManager;
import com.snowcattle.game.bootstrap.manager.spring.LocalSpringServicerAfterManager;
import com.snowcattle.game.service.net.tcp.process.GameTcpMessageProcessor;
import com.snowcattle.game.service.net.tcp.process.GameUdpMessageOrderProcessor;
import com.snowcattle.game.service.net.tcp.process.GameUdpMessageProcessor;

import java.util.LinkedHashMap;

/**
 * @author b053-mac
 *	本地服务管理
 */
public class LocalMananger extends AbstractLocalManager{

    public static LocalMananger instance = new LocalMananger();

    public LocalMananger() {
        services = new LinkedHashMap<Class,Object>(40,0.5f);
    }

    public static LocalMananger getInstance(){
        return instance;
    }

    private LocalSpringServiceManager localSpringServiceManager;

    private LocalSpringBeanManager localSpringBeanManager;

    private LocalSpringServicerAfterManager localSpringServicerAfterManager;

    //因为这里比较常用，单独提取出来
    private GameTcpMessageProcessor gameTcpMessageProcessor;
    private GameUdpMessageOrderProcessor gameUdpMessageOrderProcessor;
    private GameUdpMessageProcessor gameUdpMessageProcessor;
    private UpdateService updateService;


    public LocalSpringBeanManager getLocalSpringBeanManager() {
        return localSpringBeanManager;
    }

    public void setLocalSpringBeanManager(LocalSpringBeanManager localSpringBeanManager) {
        this.localSpringBeanManager = localSpringBeanManager;
    }

    public LocalSpringServiceManager getLocalSpringServiceManager() {
        return localSpringServiceManager;
    }

    public void setLocalSpringServiceManager(LocalSpringServiceManager localSpringServiceManager) {
        this.localSpringServiceManager = localSpringServiceManager;
    }

    @Override
    public <T> void add(Object service, Class<T> inter) {
        super.add(service, inter);
        if (service instanceof GameTcpMessageProcessor) {
            this.gameTcpMessageProcessor = (GameTcpMessageProcessor) service;
        } else if (service instanceof GameUdpMessageOrderProcessor) {
            this.gameUdpMessageOrderProcessor = (GameUdpMessageOrderProcessor) service;
        } else if (service instanceof GameUdpMessageProcessor) {
            this.gameUdpMessageProcessor = (GameUdpMessageProcessor) service;
        } else if (service instanceof UpdateService) {
            this.updateService = (UpdateService) service;
        }

    }

    public GameTcpMessageProcessor getGameTcpMessageProcessor() {
        return gameTcpMessageProcessor;
    }

    public void setGameTcpMessageProcessor(GameTcpMessageProcessor gameTcpMessageProcessor) {
        this.gameTcpMessageProcessor = gameTcpMessageProcessor;
    }

    public GameUdpMessageOrderProcessor getGameUdpMessageOrderProcessor() {
        return gameUdpMessageOrderProcessor;
    }

    public void setGameUdpMessageOrderProcessor(GameUdpMessageOrderProcessor gameUdpMessageOrderProcessor) {
        this.gameUdpMessageOrderProcessor = gameUdpMessageOrderProcessor;
    }

    public GameUdpMessageProcessor getGameUdpMessageProcessor() {
        return gameUdpMessageProcessor;
    }

    public void setGameUdpMessageProcessor(GameUdpMessageProcessor gameUdpMessageProcessor) {
        this.gameUdpMessageProcessor = gameUdpMessageProcessor;
    }

    public LocalSpringServicerAfterManager getLocalSpringServicerAfterManager() {
        return localSpringServicerAfterManager;
    }

    public void setLocalSpringServicerAfterManager(LocalSpringServicerAfterManager localSpringServicerAfterManager) {
        this.localSpringServicerAfterManager = localSpringServicerAfterManager;
    }

    public UpdateService getUpdateService() {
        return updateService;
    }

    public void setUpdateService(UpdateService updateService) {
        this.updateService = updateService;
    }

}
