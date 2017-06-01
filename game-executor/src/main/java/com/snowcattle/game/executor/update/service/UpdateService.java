package com.snowcattle.game.executor.update.service;

import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.common.utils.Loggers;
import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.impl.event.CreateEvent;
import com.snowcattle.game.executor.event.impl.event.FinishEvent;
import com.snowcattle.game.executor.event.impl.event.FinishedEvent;
import com.snowcattle.game.executor.event.impl.event.ReadFinishEvent;
import com.snowcattle.game.executor.update.cache.UpdateEventCacheService;
import com.snowcattle.game.executor.update.entity.IUpdate;
import com.snowcattle.game.executor.update.pool.IUpdateExecutor;
import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/1/12.
 *  负责循环更新服务
 *  记录更新器缓存
 *  分配事件到分配线程
 *  启动分配线程还有更新线程服务器
 */
public class UpdateService <ID extends Serializable> {

    /**
     * 负责所有update接口的调用
     */
    private DispatchThread dispatchThread;

    /**
     * 负责update的执行器
     */
    private IUpdateExecutor iUpdateExecutor;

    /*记录当前循环的更新接口*/
    private ConcurrentHashMap<ID, IUpdate> updateMap = new ConcurrentHashMap<ID, IUpdate>();

    public UpdateService(DispatchThread dispatchThread, IUpdateExecutor iUpdateExecutor) {
        this.dispatchThread = dispatchThread;
        this.iUpdateExecutor = iUpdateExecutor;
    }

    public void addReadyCreateEvent(CycleEvent event){
        EventParam[] eventParams = event.getParams();
        IUpdate  iUpdate = (IUpdate) eventParams[0].getT();
        updateMap.put((ID) event.getId(), iUpdate);
        //通知dispatchThread
        if(Loggers.gameExecutorUtil.isDebugEnabled()) {
            Loggers.gameExecutorUtil.debug("readycreate " + iUpdate.getUpdateId() + " dispatch");
        }
        CreateEvent createEvent = new CreateEvent(Constants.EventTypeConstans.createEventType, event.getId(), eventParams);
        dispatchThread.addCreateEvent(createEvent);
        dispatchThread.unpark();
    }

    public void addReadyFinishEvent(CycleEvent event){
        ReadFinishEvent readFinishEvent = (ReadFinishEvent) event;
        EventParam[] eventParams = event.getParams();
        //通知dispatchThread
        FinishEvent finishEvent = new FinishEvent(Constants.EventTypeConstans.finishEventType, event.getId(), eventParams);
        dispatchThread.addFinishEvent(finishEvent);
    }

    public void addFinishedEvent(CycleEvent event){
        FinishedEvent readFinishEvent = (FinishedEvent) event;
        EventParam[] eventParams = event.getParams();
        IUpdate  iUpdate = (IUpdate) eventParams[0].getT();
        //只有distpatch转发结束后，才会才缓存池里销毁
        updateMap.remove(event.getId(), iUpdate);
    }

    public void stop(){
        iUpdateExecutor.shutdown();
        dispatchThread.shutDown();
        this.updateMap.clear();
        UpdateEventCacheService.stop();
    }

    public void start(){
        UpdateEventCacheService.start();
        dispatchThread.startup();
        iUpdateExecutor.startup();
        dispatchThread.start();
        this.updateMap.clear();
    }

    public void notifyStart(){
        UpdateEventCacheService.start();
        iUpdateExecutor.startup();
        this.updateMap.clear();
    }

    public UpdateService(IUpdateExecutor iUpdateExecutor) {
        this.iUpdateExecutor = iUpdateExecutor;
    }

    public void notifyRun(){
        dispatchThread.notifyRun();
    }

}
