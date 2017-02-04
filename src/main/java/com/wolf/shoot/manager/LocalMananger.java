package com.wolf.shoot.manager;

/**
 * Created by jwp on 2017/2/4.
 */

import com.wolf.shoot.config.GameServerConfigService;
import com.wolf.shoot.constant.Loggers;
import com.wolf.shoot.service.IService;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author b053-mac
 *	本地服务管理
 */
public class LocalMananger implements ILocalManager{

    protected static final Logger log = Loggers.serverLogger;

    //因为下面底层调用很频繁，所以这里缓存
    protected GameServerConfigService gameServerConfigService;

    public static LocalMananger instance = new LocalMananger();

    protected static Map<Class,Object> services;

    public LocalMananger() {
        services = new LinkedHashMap<Class,Object>(40,0.5f);
    }

    public static LocalMananger getInstance(){
        return instance;
    }

    @Override
    public <X,Y extends X> void create(Class<Y> clazz,Class<X> inter) throws Exception{
        log.info(clazz.getSimpleName() + " is create");
        Object newObject = clazz.newInstance();
        if(newObject instanceof IService){
            ((IService)newObject).startup();
        }
        add(newObject,inter);
    }

    @Override
    public <T> void add(Object service, Class<T> inter) {
        log.info(service.getClass().getSimpleName() + " is add");
        if(service.getClass()!=inter&&!inter.isAssignableFrom(service.getClass())) //接口和实现类必须相等或者继承关系
            throw new IllegalArgumentException();
        services.put(inter, service);

        if(service instanceof GameServerConfigService){
            gameServerConfigService= (GameServerConfigService) service;
        }
    }


    @Override
    public <T> T get(Class<T> clazz) {
        return (T)services.get(clazz);
    }

    @Override
    public void shutdown() {
        Object[] ss = new Object[services.size()];
        services.values().toArray(ss);
        for(int i=ss.length-1;i>0;i--){
            if(ss[i] instanceof IService) {
                try {
                    ((IService)ss[i]).shutdown();
                } catch (Exception e) {
                    Loggers.errorLogger.error(e.toString(),e);
                }
            }
        }
    }

    public GameServerConfigService getGameServerConfigService() {
        return gameServerConfigService;
    }

}
