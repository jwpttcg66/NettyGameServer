package com.snowcattle.game.executor.event.impl.listener;

import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.common.IEvent;
import com.snowcattle.game.executor.event.impl.event.FinishEvent;
import com.snowcattle.game.executor.event.impl.event.UpdateEvent;
import com.snowcattle.game.executor.update.cache.UpdateEventCacheService;
import com.snowcattle.game.executor.update.entity.IUpdate;
import com.snowcattle.game.executor.update.pool.IUpdateExecutor;
import com.snowcattle.game.executor.update.service.UpdateService;
import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;

/**
 * Created by jiangwenping on 17/1/11.
 */
public class DispatchUpdateEventListener extends UpdateEventListener {
    private DispatchThread dispatchThread;
    private UpdateService updateService;
    public DispatchUpdateEventListener(DispatchThread dispatchThread, UpdateService updateService) {
        this.dispatchThread = dispatchThread;
        this.updateService = updateService;
    }


    public void fireEvent(IEvent event) {
//        if(Loggers.gameExecutorUtil.isDebugEnabled()){
//            Loggers.gameExecutorUtil.debug("处理update");
//        }
        super.fireEvent(event);

        //提交执行线程
        CycleEvent cycleEvent = (CycleEvent) event;
        EventParam[] eventParams = event.getParams();
        for(EventParam eventParam: eventParams) {
            IUpdate iUpdate = (IUpdate) eventParam.getT();
            boolean aliveFlag = cycleEvent.isUpdateAliveFlag();
            if (aliveFlag) {
                IUpdateExecutor iUpdateExecutor = dispatchThread.getiUpdateExecutor();
                iUpdateExecutor.executorUpdate(dispatchThread, iUpdate, cycleEvent.isInitFlag(), cycleEvent.getUpdateExcutorIndex());
            } else {
                FinishEvent finishEvent = new FinishEvent(Constants.EventTypeConstans.finishEventType, iUpdate.getUpdateId(), eventParams);
                dispatchThread.addFinishEvent(finishEvent);
            }
        }

        //如果是update，需要释放cache
        if(cycleEvent instanceof UpdateEvent){
            UpdateEvent updateEvent = (UpdateEvent) cycleEvent;
            UpdateEventCacheService.releaseUpdateEvent(updateEvent);
        }
    }
}
