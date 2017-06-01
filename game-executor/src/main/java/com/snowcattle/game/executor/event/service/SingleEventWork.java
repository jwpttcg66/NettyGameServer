package com.snowcattle.game.executor.event.service;

import com.snowcattle.game.executor.common.utils.Loggers;
import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.SingleEvent;
import com.snowcattle.game.thread.worker.AbstractWork;

/**
 * Created by jwp on 2017/5/5.
 * 单事件worker
 */
public class SingleEventWork extends AbstractWork{

    private EventBus eventBus;
    private SingleEvent singleEvent;

    public SingleEventWork(EventBus eventBus, SingleEvent singleEvent) {
        this.eventBus = eventBus;
        this.singleEvent = singleEvent;
    }

    @Override
    public void run() {
        try {
            eventBus.handleSingleEvent(singleEvent);
        }catch (Exception e){
            Loggers.gameExecutorError.error(e.toString(), e);
        }

    }
}
