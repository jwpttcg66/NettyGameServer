package com.wolf.shoot.common.thread.policy;

import com.wolf.shoot.common.constant.Loggers;
import org.slf4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by jwp on 2017/3/8.
 */
public class RejectedPolicy implements RejectedExecutionHandler {
    private static final Logger logger = Loggers.threadLogger;

    private final String threadName;

    public RejectedPolicy() {
        this(null);
    }

    public RejectedPolicy(String threadName) {
        this.threadName = threadName;
    }

    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
        if (threadName != null) {
            logger.error("RPC Thread pool [{}] is exhausted, executor={}", threadName, executor.toString());
        }

        if (runnable instanceof RejectedRunnable) {
            ((RejectedRunnable) runnable).rejected();
        } else {
            if (!executor.isShutdown()) {
                BlockingQueue<Runnable> queue = executor.getQueue();
                int discardSize = queue.size() >> 1;
                for (int i = 0; i < discardSize; i++) {
                    queue.poll();
                }

                try {
                    queue.put(runnable);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}