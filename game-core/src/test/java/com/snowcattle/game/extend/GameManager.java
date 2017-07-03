package com.snowcattle.game.extend;

import com.snowcattle.game.bootstrap.manager.AbstractGameManager;

import java.util.LinkedHashMap;

/**
 * Created by jwp on 2017/5/6.
 */
public class GameManager extends AbstractGameManager{
    public static GameManager instance = new GameManager();
    public GameManager() {
        services = new LinkedHashMap<Class,Object>(40,0.5f);
    }
    public static GameManager getInstance(){
        return instance;
    }

    private LocalSpringBeanGameManager localSpringBeanGameManager;
    private LocalSpringServiceGameManager localSpringServiceGameManager;

    public LocalSpringBeanGameManager getLocalSpringBeanGameManager() {
        return localSpringBeanGameManager;
    }

    public void setLocalSpringBeanGameManager(LocalSpringBeanGameManager localSpringBeanGameManager) {
        this.localSpringBeanGameManager = localSpringBeanGameManager;
    }

    public LocalSpringServiceGameManager getLocalSpringServiceGameManager() {
        return localSpringServiceGameManager;
    }

    public void setLocalSpringServiceGameManager(LocalSpringServiceGameManager localSpringServiceGameManager) {
        this.localSpringServiceGameManager = localSpringServiceGameManager;
    }
}
