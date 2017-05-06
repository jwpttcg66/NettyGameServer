package com.wolf.shoot.extend;

import com.wolf.shoot.manager.AbstractGameManager;
import com.wolf.shoot.manager.AbstractLocalManager;

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

}
