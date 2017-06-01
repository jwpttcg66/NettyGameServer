package com.snowcattle.game.executor.common.cache;

import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.impl.event.UpdateEvent;
import com.snowcattle.game.executor.event.impl.listener.DispatchCreateEventListener;
import com.snowcattle.game.executor.event.impl.listener.DispatchFinishEventListener;
import com.snowcattle.game.executor.event.impl.listener.DispatchUpdateEventListener;
import com.snowcattle.game.executor.update.cache.UpdateEventCacheService;
import com.snowcattle.game.executor.update.pool.DisruptorExecutorService;
import com.snowcattle.game.executor.update.service.UpdateService;
import com.snowcattle.game.executor.update.thread.dispatch.DisruptorDispatchThread;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/4/26.
 */
public class CacheTest {
    public static void main(String[] args) throws Exception {
        EventBus updateEventBus = new EventBus();
        int maxSize = 1;
        int corePoolSize = 1;
        long keepAliveTime = 60;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        String poolName = "update";
        DisruptorExecutorService disruptorExcutorService = new DisruptorExecutorService(poolName, corePoolSize);
        int cycleSleepTime = 1000 / Constants.cycle.cycleSize;
        DisruptorDispatchThread dispatchThread = new DisruptorDispatchThread(updateEventBus, disruptorExcutorService
                , cycleSleepTime, cycleSleepTime*1000);
        disruptorExcutorService.setDisruptorDispatchThread(dispatchThread);
        UpdateService updateService = new UpdateService(dispatchThread, disruptorExcutorService);

        updateEventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
        updateEventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
        updateEventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));

//        updateService.notifyStart();
        updateService.start();

        int size = 200000;
        for(int i = 0; i < size; i++){
            try {
                UpdateEvent updateEvent = UpdateEventCacheService.createUpdateEvent();
                System.out.println(i);
                UpdateEventCacheService.releaseUpdateEvent(updateEvent);
//            System.out.println(updateEvent);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        while (true) {
            Thread.currentThread().sleep(100);
            updateService.toString();
        }
    }

}
