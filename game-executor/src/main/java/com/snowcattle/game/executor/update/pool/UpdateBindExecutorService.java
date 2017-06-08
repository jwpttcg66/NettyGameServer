package com.snowcattle.game.executor.update.pool;

import com.snowcattle.game.common.utils.ExecutorUtil;
import com.snowcattle.game.executor.update.pool.excutor.BindThreadUpdateExecutorService;
import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;
import com.snowcattle.game.executor.update.entity.IUpdate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jwp on 2017/2/23.
 * 绑定线程更新服务
 */
public class UpdateBindExecutorService implements IUpdateExecutor {

    private int excutorSize;

    private BindThreadUpdateExecutorService[] bindThreadUpdateExecutorServices;

    private final AtomicInteger idx = new AtomicInteger();

    private DispatchThread dispatchThread;

    public UpdateBindExecutorService(int excutorSize) {
        this.excutorSize = excutorSize;
    }

    public void startup() {
        bindThreadUpdateExecutorServices = new BindThreadUpdateExecutorService[excutorSize];
        for (int i = 0; i < excutorSize; i++) {
            bindThreadUpdateExecutorServices[i] = new BindThreadUpdateExecutorService(i, dispatchThread);
        }
    }

    @Override
    public void shutdown() {
        for (int i = 0; i < excutorSize; i++) {
            ExecutorUtil.shutdownAndAwaitTermination(bindThreadUpdateExecutorServices[i], 60,
                    TimeUnit.MILLISECONDS);
        }
    }

    public BindThreadUpdateExecutorService getNext() {
        return bindThreadUpdateExecutorServices[idx.getAndIncrement() % excutorSize];
    }

    @Override
    public void executorUpdate(DispatchThread dispatchThread, IUpdate iUpdate, boolean firstFlag, int updateExcutorIndex) {
        if(firstFlag) {
            BindThreadUpdateExecutorService bindThreadUpdateExecutorService = getNext();
            bindThreadUpdateExecutorService.excuteUpdate(iUpdate, firstFlag);
        }else{

        //查找老的更新器
//            BindThreadUpdateExecutorService bindThreadUpdateExecutorService = bindThreadUpdateExecutorServices[updateExcutorIndex];
            //完全随机，取消查找老的模块，使cpu更加平均。
            BindThreadUpdateExecutorService bindThreadUpdateExecutorService = getNext();
            bindThreadUpdateExecutorService.excuteUpdate(iUpdate, false);
        }
    }

    public DispatchThread getDispatchThread() {
        return dispatchThread;
    }

    public void setDispatchThread(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
    }
}
