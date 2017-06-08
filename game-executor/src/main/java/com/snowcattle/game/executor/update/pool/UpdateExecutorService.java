package com.snowcattle.game.executor.update.pool;

import com.snowcattle.game.common.utils.ExecutorUtil;
import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;
import com.snowcattle.game.executor.update.thread.update.LockSupportUpdateFuture;
import com.snowcattle.game.executor.update.thread.update.LockSupportUpdateFutureThread;
import com.snowcattle.game.executor.update.thread.listener.LockSupportUpdateFutureListener;
import com.snowcattle.game.executor.update.entity.IUpdate;
import com.snowcattle.game.thread.executor.NonOrderedQueuePoolExecutor;
import com.snowcattle.game.thread.factory.GameThreadPoolHelpFactory;
import com.snowcattle.game.thread.policy.RejectedPolicyType;

import java.util.concurrent.*;

/**
 * Created by jiangwenping on 17/1/11.
 * 更新执行器
 */
public class UpdateExecutorService implements IUpdateExecutor {

    private NonOrderedQueuePoolExecutor nonOrderedQueuePoolExecutor;


    public UpdateExecutorService(int corePoolSize, int maxSize, RejectedPolicyType rejectedPolicyType) {
        String name = Constants.Thread.UpdateExecutorService;
        GameThreadPoolHelpFactory gameThreadPoolHelpFactory = new GameThreadPoolHelpFactory();
        nonOrderedQueuePoolExecutor = new NonOrderedQueuePoolExecutor(Constants.Thread.UpdateExecutorService, corePoolSize, maxSize, gameThreadPoolHelpFactory.createPolicy(rejectedPolicyType, name));
    }

    @Override
    public void executorUpdate(DispatchThread dispatchThread, IUpdate iUpdate, boolean firstFlag, int updateExcutorIndex) {
        LockSupportUpdateFuture lockSupportUpdateFuture = new LockSupportUpdateFuture(dispatchThread);
        lockSupportUpdateFuture.addListener(new LockSupportUpdateFutureListener());
        LockSupportUpdateFutureThread lockSupportUpdateFutureThread = new LockSupportUpdateFutureThread(dispatchThread, iUpdate, lockSupportUpdateFuture);
        nonOrderedQueuePoolExecutor.execute(lockSupportUpdateFutureThread);
    }

    @Override
    public void startup() {

    }

    @Override
    public void shutdown() {
        ExecutorUtil.shutdownAndAwaitTermination(nonOrderedQueuePoolExecutor, 60,
                TimeUnit.MILLISECONDS);
    }
}
