package com.snowcattle.game.executor.update.thread.update.bind;

import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.impl.event.UpdateEvent;
import com.snowcattle.game.executor.update.cache.UpdateEventCacheService;
import com.snowcattle.game.executor.update.thread.update.UpdateThread;
import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;
import com.snowcattle.game.executor.update.entity.IUpdate;
import com.snowcattle.game.executor.common.utils.Constants;

/**
 * Created by jiangwenping on 17/1/9.
 * 带预置锁的执行器
 */
public abstract class AbstractBindingUpdateThread extends UpdateThread {

    private DispatchThread dispatchThread;

    public AbstractBindingUpdateThread(DispatchThread dispatchThread, EventBus eventBus) {
        super(eventBus);
        this.dispatchThread = dispatchThread;
    }

    public void run() {
        if(getiUpdate() != null) {
            IUpdate excutorUpdate = getiUpdate();
            excutorUpdate.update();
            setiUpdate(null);

            //事件总线增加更新完成通知
            EventParam<IUpdate> params = new EventParam<IUpdate>(excutorUpdate);
            UpdateEvent updateEvent = UpdateEventCacheService.createUpdateEvent();
            updateEvent.setEventType(Constants.EventTypeConstans.updateEventType);
            updateEvent.setId(excutorUpdate.getUpdateId());
            updateEvent.setParams(params);
//            UpdateEvent event = new UpdateEvent(Constants.EventTypeConstans.updateEventType, excutorUpdate.getUpdateId(), params);
            updateEvent.setUpdateAliveFlag(getiUpdate().isActive());
            getEventBus().addEvent(updateEvent);
        }
    }


    public DispatchThread getDispatchThread() {
        return dispatchThread;
    }
}
