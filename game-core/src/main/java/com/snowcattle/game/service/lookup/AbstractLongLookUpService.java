package com.snowcattle.game.service.lookup;

import com.snowcattle.game.common.constant.Loggers;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/2/21.
 * 抽象long型id查找内容
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
            log.debug("add T "  + t.getClass().getSimpleName() + " id "+ t.longId());
        }
        tMap.put(t.longId(), t);
    }

    @Override
    public boolean removeT(T t) {
        if(log.isDebugEnabled()){
            log.debug("remove t "  + t.getClass().getSimpleName()  + " id "+ t.longId());
        }
        return tMap.remove(t.longId(), t);
    }

}
