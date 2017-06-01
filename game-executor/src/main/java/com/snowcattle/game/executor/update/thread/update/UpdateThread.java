package com.snowcattle.game.executor.update.thread.update;

import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.update.entity.IUpdate;

/**
 * Created by jiangwenping on 17/1/9.
 * 事件更新执行器
 */
public class UpdateThread implements Runnable{

    /**
     * 事件总线
     */
    private EventBus eventBus;

    public UpdateThread(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    private IUpdate iUpdate;

    public void run() {
        if(iUpdate != null) {
            iUpdate.update();
            iUpdate = null;
        }
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public IUpdate getiUpdate() {
        return iUpdate;
    }

    public void setiUpdate(IUpdate iUpdate) {
        this.iUpdate = iUpdate;
    }
}
