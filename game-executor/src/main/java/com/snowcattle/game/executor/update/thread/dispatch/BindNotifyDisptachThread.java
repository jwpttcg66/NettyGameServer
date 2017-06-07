package com.snowcattle.game.executor.update.thread.dispatch;

import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.update.pool.UpdateBindExecutorService;

/**
 * Created by jwp on 2017/2/23.
 */
public class BindNotifyDisptachThread extends LockSupportDisptachThread{

    public BindNotifyDisptachThread(EventBus eventBus, UpdateBindExecutorService updateBindExcutorService
            , int cycleSleepTime, long minCycleTime) {
        super(eventBus, updateBindExcutorService, cycleSleepTime, minCycleTime);
    }
}
