package com.snowcattle.game.executor.update.disruptor;

import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.impl.listener.DispatchCreateEventListener;
import com.snowcattle.game.executor.event.impl.listener.DispatchFinishEventListener;
import com.snowcattle.game.executor.event.impl.listener.DispatchUpdateEventListener;
import com.snowcattle.game.executor.update.pool.DisruptorExecutorService;
import com.snowcattle.game.executor.update.service.UpdateService;
import com.snowcattle.game.executor.update.thread.dispatch.DisruptorDispatchThread;
import com.snowcattle.game.executor.update.async.IntegerUpdate;
import com.snowcattle.game.executor.common.utils.Constants;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/4/25.
 */
public class DisruptorTest {
    public static void main(String[] args) throws Exception {
        testUpdate();
    }

    public static void testUpdate() throws Exception {
        EventBus updateEventBus = new EventBus();
//        int maxSize = 10000;
//        int corePoolSize = 100;
        int maxSize = 20000;
        int corePoolSize = 20;
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
        long updateMaxSize = 100;
        for (long i = 0; i < maxSize; i++) {
            IntegerUpdate integerUpdate = new IntegerUpdate(i, updateMaxSize);
            EventParam<IntegerUpdate> param = new EventParam<IntegerUpdate>(integerUpdate);
            CycleEvent cycleEvent = new CycleEvent(Constants.EventTypeConstans.readyCreateEventType, integerUpdate.getUpdateId(), param);
            updateService.addReadyCreateEvent(cycleEvent);
        }


        while (true) {
            Thread.currentThread().sleep(100);
            updateService.toString();
        }

//        updateService.stop();
    }
}
