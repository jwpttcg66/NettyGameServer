package com.wolf.shoot.common.thread.policy;

/**
 * Created by jwp on 2017/3/8.
 */
public interface RejectedRunnable extends Runnable {
    void rejected();
}
