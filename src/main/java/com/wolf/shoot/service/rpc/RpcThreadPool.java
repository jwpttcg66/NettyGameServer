package com.wolf.shoot.service.rpc;

import com.wolf.shoot.common.ThreadNameFactory;
import com.wolf.shoot.common.annotation.BlockingQueueType;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.thread.policy.*;
import com.wolf.shoot.service.RpcSystemConfig;
import com.wolf.shoot.service.jmx.ThreadPoolMonitorProvider;
import com.wolf.shoot.service.jmx.ThreadPoolStatus;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MalformedObjectNameException;
import javax.management.ReflectionException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * Created by jwp on 2017/3/8.
 */
@Service
public class RpcThreadPool {

    private final Logger logger = Loggers.threadLogger;

    private final Timer timer = new Timer("ThreadPoolMonitor", true);
    private long monitorDelay = 1000;
    private long monitorPeriod = 3000;

    private Executor excutor;

    private RejectedExecutionHandler createPolicy() {
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

    public Executor getExecutor(int threads, int queues) {
        String name = "RpcThreadPool";
        ThreadPoolExecutor executor = new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
                createBlockingQueue(queues),
                new ThreadNameFactory(name, false), createPolicy());
        return executor;
    }

    public Executor getExecutorWithJmx(int threads, int queueSize) {
        final ThreadPoolExecutor executor = (ThreadPoolExecutor) getExecutor(threads, queueSize);
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                ThreadPoolStatus status = new ThreadPoolStatus();
                status.setPoolSize(executor.getPoolSize());
                status.setActiveCount(executor.getActiveCount());
                status.setCorePoolSize(executor.getCorePoolSize());
                status.setMaximumPoolSize(executor.getMaximumPoolSize());
                status.setLargestPoolSize(executor.getLargestPoolSize());
                status.setTaskCount(executor.getTaskCount());
                status.setCompletedTaskCount(executor.getCompletedTaskCount());

                try {
                    ThreadPoolMonitorProvider.monitor(status);
                } catch (IOException e) {
                    logger.error(e.toString(), e);
                } catch (MalformedObjectNameException e) {
                    logger.error(e.toString(), e);
                } catch (ReflectionException e) {
                    logger.error(e.toString(), e);
                } catch (MBeanException e) {
                    logger.error(e.toString(), e);
                } catch (InstanceNotFoundException e) {
                    logger.error(e.toString(), e);
                }
            }
        }, monitorDelay, monitorDelay);
        return executor;
    }
}

