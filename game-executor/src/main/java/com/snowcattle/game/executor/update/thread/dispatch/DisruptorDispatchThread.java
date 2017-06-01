package com.snowcattle.game.executor.update.thread.dispatch;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.snowcattle.game.executor.common.utils.Loggers;
import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.common.IEvent;
import com.snowcattle.game.executor.event.factory.CycleDisruptorEventFactory;
import com.snowcattle.game.executor.event.impl.event.UpdateEvent;
import com.snowcattle.game.executor.update.cache.UpdateEventCacheService;
import com.snowcattle.game.executor.update.pool.DisruptorExecutorService;
import com.snowcattle.game.executor.update.pool.IUpdateExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jiangwenping on 17/4/24.
 * 加入disruptor
 */
public class DisruptorDispatchThread extends DispatchThread{

    private RingBuffer<CycleEvent> ringBuffer;

    private int bufferSize = 1024 * 64;

    private DisruptorExecutorService disruptorExcutorService;

    private BlockingQueue<IEvent> blockingQueue;

    private boolean runningFlag = true;

    private AtomicLong total;

    private int cycleSleepTime;
    private long minCycleTime;

    public DisruptorDispatchThread(EventBus eventBus, IUpdateExecutor iUpdateExecutor,  int cycleSleepTime , long minCycleTime) {
        super(eventBus);
        this.disruptorExcutorService = (DisruptorExecutorService) iUpdateExecutor;
//        this.blockingQueue = new ArrayBlockingQueue<IEvent>(bufferSize);
        this.blockingQueue = new LinkedBlockingDeque<>(bufferSize);
        this.cycleSleepTime = cycleSleepTime;
        this.minCycleTime = minCycleTime;
        this.total = new AtomicLong();
    }

    public void initRingBuffer(){

        ringBuffer = RingBuffer.createSingleProducer(new CycleDisruptorEventFactory(), bufferSize, new BlockingWaitStrategy());
    }

    public void addUpdateEvent(IEvent event){
        putEvent(event);
    }
    public void addCreateEvent(IEvent event){
        putEvent(event);
    }

    public void addFinishEvent(IEvent event){
        putEvent(event);
    }

    public void putEvent(IEvent event){
        try {
            blockingQueue.put(event);
            total.getAndIncrement();
        } catch (InterruptedException e) {
            Loggers.gameExecutorError.error(e.toString(), e);
        }
    }
    @Override
    public void unpark() {

    }

    @Override
    void park() {

    }

    @Override
    public IUpdateExecutor getiUpdateExecutor() {
        return disruptorExcutorService;
    }

    @Override
    public void startup() {
        initRingBuffer();
    }

    public void shutDown(){
        runningFlag = false;
    }

    @Override
    public void run(){
        while (runningFlag){
            long  cycleSize = total.get();

            int i = 0;
            long startTime = System.nanoTime();
            while (i < cycleSize) {
                CycleEvent cycleEvent = null;
                try {
                    cycleEvent = (CycleEvent) blockingQueue.take();
                    dispatch(cycleEvent);
                } catch (InterruptedException e) {
                    Loggers.gameExecutorError.error(e.toString(), e);
                }
                i++;
            }

            //准备睡眠
            checkSleep(startTime);
        }
    }

    public void checkSleep(long startTime){

        long notifyTime = System.nanoTime();
        long diff = (int) (notifyTime - startTime);
        if (diff < minCycleTime && diff > 0) {
            try {
                Thread.currentThread().sleep(cycleSleepTime, (int) (diff % 999999));
            } catch (Throwable e) {
                Loggers.gameExecutorUtil.error(e.toString(), e);
            }
        }
    }

    public void dispatch(IEvent event) {
        ringBuffer = disruptorExcutorService.getDispatchRingBuffer();
        long next = ringBuffer.next();
        CycleEvent cycleEvent = (CycleEvent) event;
        CycleEvent destEvent = ringBuffer.get(next);
        destEvent.setId(cycleEvent.getId());
        destEvent.setEventType(event.getEventType());
        destEvent.setParams(event.getParams());
        destEvent.setInitFlag(cycleEvent.isInitFlag());
        destEvent.setUpdateAliveFlag(cycleEvent.isUpdateAliveFlag());
        destEvent.setUpdateExcutorIndex(cycleEvent.getUpdateExcutorIndex());
        ringBuffer.publish(next);
        total.getAndDecrement();
        if(event instanceof UpdateEvent){
            UpdateEvent updateEvent = (UpdateEvent) event;
            UpdateEventCacheService.releaseUpdateEvent(updateEvent);
        }
    }

    public RingBuffer<CycleEvent> getRingBuffer() {
        return ringBuffer;
    }

    public void setRingBuffer(RingBuffer<CycleEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public DisruptorExecutorService getDisruptorExcutorService() {
        return disruptorExcutorService;
    }

    public void setDisruptorExcutorService(DisruptorExecutorService disruptorExcutorService) {
        this.disruptorExcutorService = disruptorExcutorService;
    }


}

