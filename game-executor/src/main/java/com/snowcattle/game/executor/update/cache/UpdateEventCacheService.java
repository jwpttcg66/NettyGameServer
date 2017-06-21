package com.snowcattle.game.executor.update.cache;

import com.snowcattle.game.executor.common.utils.Loggers;
import com.snowcattle.game.executor.event.impl.event.UpdateEvent;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created by jiangwenping on 17/4/26.
 *  updateevent的缓存服务
 */
public class UpdateEventCacheService {

    public static UpdateEventCacheFactory updateEventCacheFactory;

    public static void start(){
//        int size = 1024;
        int size = 1024;
        int max = 32;
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(size * max);
        genericObjectPoolConfig.setMaxIdle(size * max);
        genericObjectPoolConfig.setMinIdle(size);
        long time = 1000 * 30;
        genericObjectPoolConfig.setMaxWaitMillis(time);
        genericObjectPoolConfig.setSoftMinEvictableIdleTimeMillis(time);
        genericObjectPoolConfig.setTestOnReturn(true);

        updateEventCacheFactory = new UpdateEventCacheFactory(new UpdateEventPoolFactory(), genericObjectPoolConfig);
    }

    public static void stop(){
        if(updateEventCacheFactory != null) {
            updateEventCacheFactory.close();
        }
    }

    public static UpdateEvent createUpdateEvent(){
        try {
            return updateEventCacheFactory.borrowObject();
        } catch (Exception e) {
            Loggers.gameExecutorError.error(e.toString(), e);
        }
        return null;
    }

    public static void releaseUpdateEvent(UpdateEvent updateEvent){
            updateEventCacheFactory.returnObject(updateEvent);
    }
}
