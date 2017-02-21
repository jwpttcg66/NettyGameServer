package com.wolf.shoot.service.lookup;

import com.wolf.shoot.common.constant.Loggers;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/2/21.
 * 抽象
 */
public abstract class AbstractLongLookUpService<T extends ILongId> implements ILongLookUpService<T>{
    protected static final Logger log = Loggers.serverStatusStatistics;

    protected ConcurrentHashMap<Long, T> tMap = new ConcurrentHashMap<Long, T>();

    @Override
    public T lookup(long id) {
        return tMap.get(id);
    }

    @Override
    public void addT(T t) {
        if(log.isDebugEnabled()){
            log.debug("add T " + t.id());
        }
        tMap.put(t.id(), t);
    }

    @Override
    public boolean removeT(T t) {
        if(log.isDebugEnabled()){
            log.debug("remove t " + t.id());
        }
        return tMap.remove(t.id(), t);
    }

}
