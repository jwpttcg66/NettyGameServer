package com.snowcattle.game.executor.update.thread.dispatch;

import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.common.IEvent;
import com.snowcattle.game.executor.update.pool.IUpdateExecutor;

/**
 * Created by jiangwenping on 17/1/9.
 * ⌚事件分配器
 */
public abstract class DispatchThread extends Thread{

    private EventBus eventBus;

    public DispatchThread(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void notifyRun(){
        eventBus.handleEvent();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void shutDown(){
        eventBus.clear();
    }

    public void addUpdateEvent(IEvent event){
        getEventBus().addEvent(event);
    }
    public void addCreateEvent(IEvent event){
        getEventBus().addEvent(event);
    }

    public void addFinishEvent(IEvent event){
        getEventBus().addEvent(event);
    }
    abstract  public void unpark();
    abstract void park();
    abstract public IUpdateExecutor getiUpdateExecutor();
    abstract public void startup();

}
