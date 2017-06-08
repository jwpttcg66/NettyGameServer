package com.snowcattle.game.executor.update.pool.excutor;

import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;
import com.snowcattle.game.executor.update.thread.update.bind.BindingUpdateThread;
import com.snowcattle.game.executor.update.entity.IUpdate;
import com.snowcattle.game.executor.update.entity.NullWeakUpUpdate;
import com.snowcattle.game.executor.common.utils.Loggers;
import com.snowcattle.game.thread.ThreadNameFactory;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by jwp on 2017/2/23.
 * 单线程执行器
 *
 * 当线程执行完所有update的时候，退出eventloop
 */
public class BindThreadUpdateExecutorService extends  FinalizableDelegatedExecutorService implements OrderedEventExecutor{


    //当前线程执行器 执行状态
    private static final int ST_NOT_STARTED = 1;
    private static final int ST_STARTED = 2;
    private static final int ST_SHUTTING_DOWN = 3;
    private static final int ST_SHUTDOWN = 4;
    private static final int ST_TERMINATED = 5;

    private final AtomicIntegerFieldUpdater<BindThreadUpdateExecutorService> STATE_UPDATER =  AtomicIntegerFieldUpdater.newUpdater(BindThreadUpdateExecutorService.class, "state");;

    private volatile int state = ST_NOT_STARTED;

    private Queue<IUpdate> updateQueue;
    private BlockingQueue<IUpdate> fetchUpdates;
    private DispatchThread dispatchThread;

    //用来唤醒updatethread
    public static final NullWeakUpUpdate nullWeakUpUpdate = new NullWeakUpUpdate();


    private int updateExcutorIndex;
    public BindThreadUpdateExecutorService(int updateExcutorIndex, DispatchThread dispatchThread) {
        super(new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadNameFactory(Constants.Thread.BindThreadUpdateExecutorService)));
        this.updateExcutorIndex = updateExcutorIndex;
        updateQueue = new ConcurrentLinkedQueue<IUpdate>();
        fetchUpdates = new LinkedBlockingQueue<IUpdate>(Integer.MAX_VALUE);
        this.dispatchThread = dispatchThread;
    }

    //执行跟辛
    public void excuteUpdate(IUpdate iUpdate, boolean initFlag){
        if(initFlag){
            startThread();
            addTaskQueue(iUpdate);
        }
        wakeUp();
    }

    public void wakeUp(){
        try {
            fetchUpdates.put(nullWeakUpUpdate);
        } catch (InterruptedException e) {
            Loggers.gameExecutorError.error(e.toString(), e);
        }
    }

    private void startThread() {
        if (STATE_UPDATER.get(this) == ST_NOT_STARTED) {
            if (STATE_UPDATER.compareAndSet(this, ST_NOT_STARTED, ST_STARTED)) {
                doStartThread();
            }
        }
    }

    //启动执行线程
    public void doStartThread(){
        BindingUpdateThread singleLockSupportUpdateThread = new BindingUpdateThread(this,dispatchThread, updateQueue, fetchUpdates);
        execute(singleLockSupportUpdateThread);
    }

    //增加到队列里面
    public void addTaskQueue(IUpdate iUpdate){
        this.updateQueue.add(iUpdate);
    }

    //删除队列
    public void removeTaskQueue(IUpdate update){
        this.updateQueue.remove(update);
    }

    public int getUpdateExcutorIndex() {
        return updateExcutorIndex;
    }
}
