package com.snowcattle.game.executor.update.thread.update;

import com.snowcattle.game.executor.update.thread.dispatch.DispatchThread;
import com.snowcattle.game.executor.update.entity.IUpdate;

/**
 * Created by jiangwenping on 17/1/18.
 *
 */
public class LockSupportUpdateFutureThread implements Runnable {

    private DispatchThread dispatchThread;
    private IUpdate iUpdate;
    private LockSupportUpdateFuture lockSupportUpdateFuture;

    public LockSupportUpdateFutureThread(DispatchThread dispatchThread, IUpdate iUpdate
        , LockSupportUpdateFuture lockSupportUpdateFuture) {
        this.dispatchThread = dispatchThread;
        this.iUpdate = iUpdate;
        this.lockSupportUpdateFuture = lockSupportUpdateFuture;
    }

    public void run() {
        if (getiUpdate() != null) {
            IUpdate excutorUpdate = getiUpdate();
            excutorUpdate.update();
            setiUpdate(null);
            lockSupportUpdateFuture.setSuccess(excutorUpdate);
        }
    }


    public DispatchThread getDispatchThread() {
        return dispatchThread;
    }

    public void setDispatchThread(DispatchThread dispatchThread) {
        this.dispatchThread = dispatchThread;
    }

    public IUpdate getiUpdate() {
        return iUpdate;
    }

    public void setiUpdate(IUpdate iUpdate) {
        this.iUpdate = iUpdate;
    }
}

