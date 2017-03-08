package com.wolf.shoot.service.rpc;

import com.wolf.shoot.common.ThreadNameFactory;
import com.wolf.shoot.common.annotation.BlockingQueueType;
import com.wolf.shoot.common.thread.policy.*;
import com.wolf.shoot.service.RpcSystemConfig;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * Created by jwp on 2017/3/8.
 */
public class RpcThreadPool {
    private static final Timer timer = new Timer("ThreadPoolMonitor", true);
    private static long monitorDelay = 1000;
    private static long monitorPeriod = 3000;

    private static RejectedExecutionHandler createPolicy() {
        RejectedPolicyType rejectedPolicyType = RejectedPolicyType.fromString(System.getProperty(RpcSystemConfig.SystemPropertyThreadPoolRejectedPolicyAttr, "AbortPolicy"));

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

    private static BlockingQueue<Runnable> createBlockingQueue(int queues) {
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

    public static Executor getExecutor(int threads, int queues) {
        String name = "RpcThreadPool";
        ThreadPoolExecutor executor = new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
                createBlockingQueue(queues),
                new ThreadNameFactory(name, false), createPolicy());
        return executor;
    }

    public static Executor getExecutorWithJmx(int threads, int queues) {
        final ThreadPoolExecutor executor = (ThreadPoolExecutor) getExecutor(threads, queues);
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
//                ThreadPoolStatus status = new ThreadPoolStatus();
//                status.setPoolSize(executor.getPoolSize());
//                status.setActiveCount(executor.getActiveCount());
//                status.setCorePoolSize(executor.getCorePoolSize());
//                status.setMaximumPoolSize(executor.getMaximumPoolSize());
//                status.setLargestPoolSize(executor.getLargestPoolSize());
//                status.setTaskCount(executor.getTaskCount());
//                status.setCompletedTaskCount(executor.getCompletedTaskCount());
//
//                try {
//                    ThreadPoolMonitorProvider.monitor(status);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (MalformedObjectNameException e) {
//                    e.printStackTrace();
//                } catch (ReflectionException e) {
//                    e.printStackTrace();
//                } catch (MBeanException e) {
//                    e.printStackTrace();
//                } catch (InstanceNotFoundException e) {
//                    e.printStackTrace();
//                }
            }
        }, monitorDelay, monitorDelay);
        return executor;
    }
}

