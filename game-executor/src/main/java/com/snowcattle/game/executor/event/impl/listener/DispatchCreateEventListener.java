package com.snowcattle.game.executor.event.impl.listener;

import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.common.IEvent;
import com.snowcattle.game.executor.event.impl.event.FinishEvent;
import com.snowcattle.game.executor.event.impl.event.UpdateEvent;
import com.snowcattle.game.executor.update.cache.UpdateEventCacheService;
import com.snowcattle.game.executor.update.service.UpdateService;
import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;
import com.snowcattle.game.executor.update.entity.IUpdate;
import com.snowcattle.game.executor.common.utils.Constants;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchCreateEventListener extends CreateEventListener {

    private DispatchThread dispatchThread;

    private UpdateService updateService;
    public DispatchCreateEventListener(DispatchThread dispatchThread, UpdateService updateService) {
        super();
        this.dispatchThread = dispatchThread;
        this.updateService = updateService;
    }

    public void fireEvent(IEvent event) {
        super.fireEvent(event);
        EventParam[] eventParams = event.getParams();
        IUpdate iUpdate = (IUpdate) eventParams[0].getT();
        if(iUpdate.isActive()) {
//            UpdateEvent updateEvent = new UpdateEvent(Constants.EventTypeConstans.updateEventType, iUpdate.getUpdateId(), event.getParams());
            UpdateEvent updateEvent = UpdateEventCacheService.createUpdateEvent();
            updateEvent.setEventType(Constants.EventTypeConstans.updateEventType);
            updateEvent.setId(iUpdate.getUpdateId());
            updateEvent.setParams(eventParams);
            updateEvent.setInitFlag(true);
            updateEvent.setUpdateAliveFlag(true);
            this.dispatchThread.addUpdateEvent(updateEvent);
        }else{
            FinishEvent finishEvent = new FinishEvent(Constants.EventTypeConstans.finishEventType, iUpdate.getUpdateId(), eventParams);
            dispatchThread.addFinishEvent(finishEvent);
        }
    }
}
