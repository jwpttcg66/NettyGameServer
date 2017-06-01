package com.snowcattle.game.executor.update.thread.update;

import com.snowcattle.future.AbstractFuture;
import com.snowcattle.future.ITaskFuture;
import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;
import com.snowcattle.game.executor.update.entity.IUpdate;

/**
 * Created by jiangwenping on 17/1/18.
 */
public class LockSupportUpdateFuture extends AbstractFuture<IUpdate> {

    private DispatchThread dispatchThread;

    public LockSupportUpdateFuture(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
    }

    @Override
    public ITaskFuture<IUpdate> setSuccess(Object result) {
        return super.setSuccess(result);
    }

    @Override
    public ITaskFuture<IUpdate> setFailure(Throwable cause) {
        return super.setFailure(cause);
    }

    public DispatchThread getDispatchThread() {
        return dispatchThread;
    }

    public void setDispatchThread(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
    }
}