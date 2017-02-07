package com.wolf.shoot.service.net;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * Created by jiangwenping on 17/2/7.
 * 因为ThreadPerTaskExecutor 为final这里需要重新写
 *
 * 自定义netty ThreadPerTaskExecutor
 */
public class GameThreadPerTaskExecutor implements Executor {

    private final ThreadFactory threadFactory;

    public GameThreadPerTaskExecutor(ThreadFactory threadFactory) {
        if(threadFactory == null) {
            throw new NullPointerException("threadFactory");
        } else {
            this.threadFactory = threadFactory;
        }
    }

    public void execute(Runnable command) {
        this.threadFactory.newThread(command).start();
    }

}
