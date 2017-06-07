package com.snowcattle.game.executor.update.thread.dispatch;

import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.update.pool.IUpdateExecutor;
import com.snowcattle.game.executor.common.utils.Loggers;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by jiangwenping on 17/1/9.
 * 带预置锁的分配器
 *  接受create, update, finish事件
 *   负责整个调度器的调度 ,按照bus里面的大小来确定每次循环多少个
 */
public class LockSupportDisptachThread extends DispatchThread {

    private boolean runningFlag = true;
    private IUpdateExecutor iUpdateExecutor;

    private int cycleSleepTime;
    private long minCycleTime;

    public LockSupportDisptachThread(EventBus eventBus, IUpdateExecutor iUpdateExecutor
            , int cycleSleepTime , long minCycleTime) {
        super(eventBus);
        this.iUpdateExecutor = iUpdateExecutor;
        this.cycleSleepTime = cycleSleepTime;
        this.minCycleTime = minCycleTime;
    }

    @Override
    public void run() {
        while (runningFlag) {
           singleCycle(true);
        }
    }

    private void singleCycle(boolean sleepFlag){
        long startTime = System.nanoTime();
        int cycleSize = getEventBus().getEventsSize();
        if(sleepFlag) {
            int size = getEventBus().cycle(cycleSize);
            park();
//            if(size != 0){
//                //调度计算
//                park();
//            }
            checkSleep(startTime);
        }else{
            int size = getEventBus().cycle(cycleSize);
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

    @Override
    public void notifyRun() {
       singleCycle(false);
    }

    public boolean isRunningFlag() {
        return runningFlag;
    }

    public void setRunningFlag(boolean runningFlag) {
        this.runningFlag = runningFlag;
    }

    public IUpdateExecutor getiUpdateExecutor() {
        return iUpdateExecutor;
    }

    @Override
    public void startup() {

    }

    public void setiUpdateExecutor(IUpdateExecutor iUpdateExecutor) {
        this.iUpdateExecutor = iUpdateExecutor;
    }

    public void shutDown(){
        this.runningFlag = false;
        super.shutDown();
    }

    @Override
    public void unpark(){
        LockSupport.unpark(this);
    }

    @Override
    public void park(){
        LockSupport.park(this);
    }
}
