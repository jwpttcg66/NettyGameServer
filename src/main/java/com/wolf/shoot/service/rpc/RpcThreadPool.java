package com.wolf.shoot.service.rpc;

import com.wolf.shoot.common.ThreadNameFactory;
import com.wolf.shoot.common.annotation.BlockingQueueType;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.thread.policy.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * Created by jwp on 2017/3/8.
 */
@Service
public class RpcThreadPool {

    private final Logger logger = Loggers.threadLogger;
    private ExecutorService excutor;

    private RejectedExecutionHandler createPolicy() {
        RejectedPolicyType rejectedPolicyType = RejectedPolicyType.fromString(System.getProperty(RpcSystemConfig.SystemPropertyThreadPoolRejectedPolicyAttr, "CallerRunsPolicy"));

        switch (rejectedPolicyType) {
            case BLOCKING_POLICY:
                return new BlockingPolicy();
            case CALLER_RUNS_POLICY:
                return new CallerRunsPolicy();
            case ABORT_POLICY:
                return new AbortPolicy();
            case REJECTED_POLICY:
                return new RejectedPolicy();
            case DISCARDED_POLICY:
                return new DiscardedPolicy();
        }

        return null;
    }

    private BlockingQueue<Runnable> createBlockingQueue(int queues) {
        BlockingQueueType queueType = BlockingQueueType.fromString(System.getProperty(RpcSystemConfig.SystemPropertyThreadPoolQueueNameAttr, "LinkedBlockingQueue"));

        switch (queueType) {
            case LINKED_BLOCKING_QUEUE:
                return new LinkedBlockingQueue<Runnable>();
            case ARRAY_BLOCKING_QUEUE:
                return new ArrayBlockingQueue<Runnable>(RpcSystemConfig.PARALLEL * queues);
            case SYNCHRONOUS_QUEUE:
                return new SynchronousQueue<Runnable>();
        }

        return null;
    }

    public Executor createExecutor(int threads, int queues) {
        String name = "RpcThreadPool";
        ThreadPoolExecutor executor = new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS, createBlockingQueue(queues), new ThreadNameFactory(name, false), createPolicy());
        this.excutor = executor;
        return executor;
    }

    public ExecutorService getExcutor() {
        return excutor;
    }

    public void setExcutor(ExecutorService excutor) {
        this.excutor = excutor;
    }
}

