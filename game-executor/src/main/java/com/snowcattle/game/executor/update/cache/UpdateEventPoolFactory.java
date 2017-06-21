package com.snowcattle.game.executor.update.cache;

import com.snowcattle.game.executor.event.impl.event.UpdateEvent;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * Created by jiangwenping on 17/4/26.
 */
public class UpdateEventPoolFactory implements PooledObjectFactory<UpdateEvent>{
    @Override
    public PooledObject<UpdateEvent> makeObject() throws Exception {
//        System.out.println("makeObject updateEvent");
        UpdateEvent updateEvent = new UpdateEvent();
        return new DefaultPooledObject<>(updateEvent);
    }

    @Override
    public void destroyObject(PooledObject<UpdateEvent> p) throws Exception {
//        System.out.println("destroyObject updateEvent");
        UpdateEvent updateEvent = p.getObject();
        updateEvent = null;
    }

    @Override
    public boolean validateObject(PooledObject<UpdateEvent> p) {
//        System.out.println("validateObject updateEvent");
        return false;
    }

    @Override
    public void activateObject(PooledObject<UpdateEvent> p) throws Exception {
//        System.out.println("active updateEvent");
    }

    @Override
    public void passivateObject(PooledObject<UpdateEvent> p) throws Exception {
//        System.out.println("passivate updateEvent");
    }
}
