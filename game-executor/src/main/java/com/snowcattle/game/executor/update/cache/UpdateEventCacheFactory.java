package com.snowcattle.game.executor.update.cache;

import com.snowcattle.game.executor.event.impl.event.UpdateEvent;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Created by jiangwenping on 17/4/26.
 * updateevent因为使用太频繁，使用commonpool2缓存
 */
public class UpdateEventCacheFactory extends GenericObjectPool<UpdateEvent> {

    public UpdateEventCacheFactory(PooledObjectFactory<UpdateEvent> factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }
}
