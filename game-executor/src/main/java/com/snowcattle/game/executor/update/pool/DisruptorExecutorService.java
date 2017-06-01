package com.snowcattle.game.executor.update.pool;

import com.lmax.disruptor.FatalExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkerPool;
import com.snowcattle.game.executor.update.cache.UpdateEventCacheService;
import com.snowcattle.game.executor.update.entity.IUpdate;
import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.handler.CycleEventHandler;
import com.snowcattle.game.executor.event.impl.event.UpdateEvent;
import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;
import com.snowcattle.game.executor.update.thread.dispatch.DisruptorDispatchThread;
import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.thread.executor.NonOrderedQueuePoolExecutor;

import java.util.concurrent.ExecutorService;

/**
 * Created by jiangwenping on 17/4/24.
 */
public class DisruptorExecutorService implements IUpdateExecutor {

    private WorkerPool workerPool;

    private int excutorSize;

    private CycleEventHandler[] cycleEventHandler;

    private DisruptorDispatchThread disruptorDispatchThread ;

    private ExecutorService executorService;

    private String poolName;
    public DisruptorExecutorService(String poolName, int excutorSize) {
        this.excutorSize = excutorSize;
        this.poolName = poolName;
    }

    @Override
    public void executorUpdate(DispatchThread dispatchThread, IUpdate iUpdate, boolean firstFlag, int updateExcutorIndex) {
        iUpdate.update();

        //事件总线增加更新完成通知
        EventParam<IUpdate> params = new EventParam<IUpdate>(iUpdate);
        UpdateEvent updateEvent = UpdateEventCacheService.createUpdateEvent();
        updateEvent.setEventType(Constants.EventTypeConstans.updateEventType);
        updateEvent.setId(iUpdate.getUpdateId());
        updateEvent.setParams(params);
//        UpdateEvent event = new UpdateEvent(Constants.EventTypeConstans.updateEventType, iUpdate.getUpdateId(), params);
        updateEvent.setUpdateAliveFlag(iUpdate.isActive());
        disruptorDispatchThread.addUpdateEvent(updateEvent);
    }

    @Override
    public void startup() {
        EventBus eventBus = disruptorDispatchThread.getEventBus();
        executorService = new NonOrderedQueuePoolExecutor(poolName, excutorSize);
        cycleEventHandler = new CycleEventHandler[excutorSize];
        for(int i = 0; i < excutorSize; i++){
            cycleEventHandler[i] = new CycleEventHandler(eventBus);
        }

        RingBuffer ringBuffer = disruptorDispatchThread.getRingBuffer();
        workerPool = new WorkerPool(ringBuffer, ringBuffer.newBarrier(), new FatalExceptionHandler(), cycleEventHandler);
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        workerPool.start(executorService);

//        BatchEventProcessor<CycleEvent>[] batchEventProcessors = new BatchEventProcessor[excutorSize];
//        for(int i = 0; i < excutorSize; i++){
//            batchEventProcessors[i] = new BatchEventProcessor<>(ringBuffer, sequenceBarrier, cycleEventHandler[i]);
//            ringBuffer.addGatingSequences(batchEventProcessors[i].getSequence());
////            executorService.submit(batchEventProcessors[i]);
//        }
    }

    @Override
    public void shutdown() {
        workerPool.drainAndHalt();
    }

    public DisruptorDispatchThread getDisruptorDispatchThread() {
        return disruptorDispatchThread;
    }

    public void setDisruptorDispatchThread(DisruptorDispatchThread disruptorDispatchThread) {
        this.disruptorDispatchThread = disruptorDispatchThread;
    }

    public WorkerPool getWorkerPool() {
        return workerPool;
    }

    public void setWorkerPool(WorkerPool workerPool) {
        this.workerPool = workerPool;
    }

    public RingBuffer getDispatchRingBuffer(){
        return disruptorDispatchThread.getRingBuffer();
    }
}
