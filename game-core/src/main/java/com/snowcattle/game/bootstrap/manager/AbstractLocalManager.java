package com.snowcattle.game.bootstrap.manager;

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.service.IService;
import org.slf4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jwp on 2017/5/6.
 */
public abstract class AbstractLocalManager implements ILocalManager{

    protected static final Logger log = Loggers.serverLogger;


    protected Map<Class,Object> services;

    public AbstractLocalManager() {
        services = new LinkedHashMap<Class,Object>(40,0.5f);
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
        services.put(inter, service);

    }


    @Override
    public <T> T get(Class<T> clazz) {
        return (T)services.get(clazz);
    }

    @Override
    public void shutdown() {
//        Object[] ss = new Object[services.size()];
//        services.values().toArray(ss);
        Object[] ss = services.values().toArray(new Object[0]);
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
}
