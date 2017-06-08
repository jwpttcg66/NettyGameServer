package com.snowcattle.game.executor.event.service;

import com.snowcattle.game.common.util.ErrorsUtil;
import com.snowcattle.game.common.utils.ExecutorUtil;
import com.snowcattle.game.executor.common.utils.CommonErrorInfo;
import com.snowcattle.game.executor.common.utils.Loggers;
import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.SingleEvent;
import com.snowcattle.game.expression.Expression;
import com.snowcattle.game.expression.ExpressionUtil;
import com.snowcattle.game.thread.ThreadNameFactory;
import com.snowcattle.game.thread.executor.OrderedQueuePoolExecutor;
import com.snowcattle.game.thread.factory.GameThreadPoolHelpFactory;
import com.snowcattle.game.thread.policy.RejectedPolicyType;
import org.slf4j.Logger;

import java.util.concurrent.*;

/**
 * Created by jwp on 2017/4/27.
 * 异步事件服务
 */
public class AsyncEventService {

    private Logger eventLogger = Loggers.gameExecutorEvent;

    private EventBus eventBus;

    private BlockingQueue<SingleEvent> queue;

    private OrderedQueuePoolExecutor orderedQueuePoolExecutor;


    /** 处理的消息总数 */
    public long statisticsMessageCount = 0;

    /**work线程池大小*/
    private int workSize;

    /**事件异步处理线程池大小*/
    private int handlerSize;

    private Expression shardingExpresson;

    private String threadFactoryName;
    private int orderQueueMaxSize;


    /** 消息处理线程池 */
    private volatile ExecutorService executorService;
    private String workThreadFactoryName;
    /**
     *
     * @param eventBus
     * @param queueSize 生产者队列大小
     * @param workSize  消费者工作线程
     * @param threadFactoryName 执行线程池名字
     * @param orderQueueMaxSize  顺序执行线程池队列大小
     */
    public AsyncEventService(EventBus eventBus, int queueSize, int workSize, String workThreadFactoryName, int handlerSize, String threadFactoryName, int orderQueueMaxSize) {
        this.eventBus = eventBus;
        queue = new ArrayBlockingQueue<SingleEvent>(queueSize);
        this.workSize = workSize;
        this.workThreadFactoryName = workThreadFactoryName;
        this.handlerSize = handlerSize;
        this.threadFactoryName = threadFactoryName;
        this.orderQueueMaxSize = orderQueueMaxSize;
    }

    public void startUp() throws Exception {

        ThreadNameFactory factory = new ThreadNameFactory(workThreadFactoryName);
        this.executorService = Executors
                .newFixedThreadPool(this.workSize, factory);

        for (int i = 0; i < this.workSize; i++) {
            this.executorService.execute(new Worker());
        }

        if (this.orderedQueuePoolExecutor != null) {
            throw new IllegalStateException(
                    "AsyncEventService The executorSerive has not been stopped.");
        }
        GameThreadPoolHelpFactory gameThreadPoolHelpFactory = new GameThreadPoolHelpFactory();
        orderedQueuePoolExecutor = new OrderedQueuePoolExecutor(threadFactoryName, handlerSize, orderQueueMaxSize, gameThreadPoolHelpFactory.createPolicy(RejectedPolicyType.BLOCKING_POLICY, workThreadFactoryName));
        String expressionString = "${0}%" + handlerSize;
        shardingExpresson = ExpressionUtil.buildExpression(expressionString);
        eventLogger.info("AsyncEventService processor executorService started ["
                + this.orderedQueuePoolExecutor + " with " + this.handlerSize
                + " threads ]");
    }

    public void shutDown(){

        eventLogger.info("AsyncEventService eventbus" + this + " stopping ...");
        eventBus.clear();
        eventLogger.info("AsyncEventService worker" + this + " stopping ...");
        if (this.executorService != null) {
            ExecutorUtil.shutdownAndAwaitTermination(this.executorService, 60,
                    TimeUnit.MILLISECONDS);
            this.executorService = null;
        }

        eventLogger.info("AsyncEventService handle thread" + this + " stopping ...");
        if (this.orderedQueuePoolExecutor != null) {
            ExecutorUtil.shutdownAndAwaitTermination(this.orderedQueuePoolExecutor, 60,
                    TimeUnit.MILLISECONDS);
            this.orderedQueuePoolExecutor = null;
        }

        eventLogger.info("AsyncEventService" + this + " stopped");
    }


    /**
     * 处理具体的消息，消息
     *
     * @param event
     */
    public void put(SingleEvent event) {
        try {
            queue.put(event);
            if (eventLogger.isDebugEnabled()) {
                eventLogger.debug("put queue size:" + queue.size());
            }
        } catch (InterruptedException e) {
            if (eventLogger.isErrorEnabled()) {
                eventLogger.error(CommonErrorInfo.THRAD_ERR_INTERRUPTED, e);
            }
        }
    }

    /**
     * 处理具体的事件
     *
     * @param event
     */
    @SuppressWarnings("unchecked")
    public void process(SingleEvent event) {
        if (event == null) {
            if (eventLogger.isWarnEnabled()) {
                eventLogger.warn("[#CORE.QueueMessageExecutorProcessor.process] ["
                        + CommonErrorInfo.EVENT_PRO_NULL_MSG + "]");
            }
            return;
        }
        long begin = 0;
        if (eventLogger.isInfoEnabled()) {
            begin = System.nanoTime();
        }
        this.statisticsMessageCount++;
        try {
             long shardignId = event.getShardingId();
             long shardingResult = shardingExpresson.getValue(shardignId);
             orderedQueuePoolExecutor.addTask(shardingResult, new SingleEventWork(eventBus, event));
        } catch (Exception e) {
            if (eventLogger.isErrorEnabled()) {
                eventLogger.error(ErrorsUtil.error("Error",
                        "#.AsyncEventService.process", "param"), e);
            }

        } finally {
            if (eventLogger.isInfoEnabled()) {
                // 特例，统计时间跨度
                long time = (System.nanoTime() - begin) / (1000 * 1000);
                if (time > 1) {
                    eventLogger.info("#AsyncEventService disptach event id:" + event.getId(), " shardingId:"
                            + event.getShardingId() + " Time:"
                            + time + "ms" + " Total:"
                            + this.statisticsMessageCount);
                }
            }
        }
    }

    private final class Worker implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    process(queue.take());
                    if (eventLogger.isDebugEnabled()) {
                        eventLogger.debug("run queue size:" + queue.size());
                    }
                } catch (InterruptedException e) {
                    if (eventLogger.isWarnEnabled()) {
                        eventLogger
                                .warn("[#CORE.AsyncEventService.run] [Stop process]");
                    }
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    eventLogger.error(CommonErrorInfo.EVENT_PRO_ERROR, e);
                }
            }
        }
    }
}
