package com.snowcattle.game.executor.update.async;

/**
 * Created by jiangwenping on 17/1/12.
 */

import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.impl.listener.DispatchCreateEventListener;
import com.snowcattle.game.executor.event.impl.listener.DispatchFinishEventListener;
import com.snowcattle.game.executor.event.impl.listener.DispatchUpdateEventListener;
import com.snowcattle.game.executor.update.pool.UpdateExecutorService;
import com.snowcattle.game.executor.update.service.UpdateService;
import com.snowcattle.game.executor.update.thread.dispatch.LockSupportDisptachThread;
import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.thread.policy.RejectedPolicyType;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class AsyncUpdateBusTest {
    public static void main(String[] args) throws Exception {
        testUpdate();
    }

    public static void testUpdate() throws Exception {
        EventBus updateEventBus = new EventBus();
//        int maxSize = 10000;
//        int corePoolSize = 100;
        int maxSize = 20;
        int corePoolSize = 60;
        UpdateExecutorService updateExecutorService = new UpdateExecutorService(corePoolSize, corePoolSize * 2, RejectedPolicyType.BLOCKING_POLICY);
        int cycleSleepTime = 1000 / Constants.cycle.cycleSize;
        LockSupportDisptachThread dispatchThread = new LockSupportDisptachThread(updateEventBus, updateExecutorService
                , cycleSleepTime, cycleSleepTime * 1000000);
        UpdateService updateService = new UpdateService(dispatchThread, updateExecutorService);
        updateEventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
        updateEventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
        updateEventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));

//        updateEventBus.addEventListener(new ReadyCreateEventListener());
//        updateEventBus.addEventListener(new ReadyFinishEventListener());

//        dispatchThread.startup();
        updateService.start();
        long updateMaxSize = 60;
        for (long i = 0; i < maxSize; i++) {
            IntegerUpdate integerUpdate = new IntegerUpdate(i, updateMaxSize);
            EventParam<IntegerUpdate> param = new EventParam<IntegerUpdate>(integerUpdate);
            CycleEvent cycleEvent = new CycleEvent(Constants.EventTypeConstans.readyCreateEventType, integerUpdate.getUpdateId(), param);
            updateService.addReadyCreateEvent(cycleEvent);
        }

//        while (true){
//            Thread.currentThread().sleep(100);
//            updateService.toString();
//        }
//        updateService.shutDown();
        while (true) {
            Thread.currentThread().sleep(100);
            updateService.toString();
        }

    }
}