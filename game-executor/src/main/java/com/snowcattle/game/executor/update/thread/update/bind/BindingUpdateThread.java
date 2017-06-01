package com.snowcattle.game.executor.update.thread.update.bind;

import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.impl.event.UpdateEvent;
import com.snowcattle.game.executor.update.cache.UpdateEventCacheService;
import com.snowcattle.game.executor.update.pool.excutor.BindThreadUpdateExecutorService;
import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;
import com.snowcattle.game.executor.update.entity.IUpdate;
import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.common.utils.Loggers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by jwp on 2017/2/23.
 * 线程一旦启动不会停止,使用arrayblockqueue进行阻塞fetchUpdates，
 * 并且通过加入一个null update来进行wakeup
 */
public class BindingUpdateThread extends AbstractBindingUpdateThread {

    private Queue<IUpdate> iUpdates;
    //这里会用来阻塞
    private BlockingQueue<IUpdate> fetchUpdates;

    private int fetchSize;
    private int updateSize;

    private List<IUpdate> finishList;

    private BindThreadUpdateExecutorService bindThreadUpdateExecutorService;
    public BindingUpdateThread(BindThreadUpdateExecutorService bindThreadUpdateExecutorService, DispatchThread dispatchThread, Queue<IUpdate> iUpdates, BlockingQueue<IUpdate> fetchUpdates) {
        super(dispatchThread, dispatchThread.getEventBus());
        this.bindThreadUpdateExecutorService = bindThreadUpdateExecutorService;
        this.iUpdates = iUpdates;
        this.fetchUpdates = fetchUpdates;
        this.finishList = new ArrayList<IUpdate>();
    }

    @Override
    public void run() {

        for (; ; ) {
            fetchUpdates();
            for (; ; ) {

                try {
                    IUpdate excutorUpdate = fetchUpdates.take();
                    if (excutorUpdate != null) {
                        if(excutorUpdate == BindThreadUpdateExecutorService.nullWeakUpUpdate){
                            continue;
                        }
                        excutorUpdate.update();
                        updateSize++;
                        finishList.add(excutorUpdate);
                    }else{
                        break;
                    }
                } catch (Exception e) {
                    Loggers.gameExecutorError.error(e.toString(), e);
                    break;
                }

                if(updateSize == fetchSize){
                    sendFinishList();
                    break;
                }

            }
            cleanFetch();

            //这里会运行的太快，需要阻塞
            try {
                fetchUpdates.take();
            } catch (InterruptedException e) {
                Loggers.gameExecutorError.error(e.toString(), e);
            }
        }

    }

    public void cleanFetch(){
        fetchSize = 0;
        updateSize = 0;
    }

    public void fetchUpdates() {
        Iterator<IUpdate> iUpdateIterator = iUpdates.iterator();
        while (iUpdateIterator.hasNext()){
            fetchUpdates.add(iUpdateIterator.next());
            fetchSize++;
        }
    }

    public void sendFinish(IUpdate excutorUpdate) {
        //如果生命周期结束了，直接进行销毁
        if(!excutorUpdate.isActive()){
            bindThreadUpdateExecutorService.removeTaskQueue(excutorUpdate);
        }
        //事件总线增加更新完成通知
        EventParam<IUpdate> params = new EventParam<IUpdate>(excutorUpdate);
        UpdateEvent updateEvent = UpdateEventCacheService.createUpdateEvent();
        updateEvent.setEventType(Constants.EventTypeConstans.updateEventType);
        updateEvent.setId(excutorUpdate.getUpdateId());
        updateEvent.setParams(params);
        updateEvent.setUpdateExcutorIndex(bindThreadUpdateExecutorService.getUpdateExcutorIndex());
        updateEvent.setUpdateAliveFlag(excutorUpdate.isActive());
        getEventBus().addEvent(updateEvent);

    }

    public void sendFinishList(){

        //事件总线增加更新完成通知
        for(IUpdate excutorUpdate : finishList){
//            if(Loggers.gameExecutorUtil.isDebugEnabled()) {
//                Loggers.gameExecutorUtil.debug(executorUpdate.getUpdateId() + "发送存活" + executorUpdate.isActive());
//            }
            sendFinish(excutorUpdate);
        }
        finishList.clear();

    }

    public void addUpdate(IUpdate iUpdate){
        iUpdates.add(iUpdate);
    }


}
