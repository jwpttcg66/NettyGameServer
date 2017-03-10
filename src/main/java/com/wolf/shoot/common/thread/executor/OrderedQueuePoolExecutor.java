package com.wolf.shoot.common.thread.executor;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.thread.worker.AbstractWork;
import com.wolf.shoot.common.thread.worker.OrderedQueuePool;
import com.wolf.shoot.common.thread.worker.TasksQueue;
import org.slf4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/3/10.
 */
public class OrderedQueuePoolExecutor extends ThreadPoolExecutor {

    protected Logger logger = Loggers.threadLogger;

    private OrderedQueuePool<String, AbstractWork> pool = new OrderedQueuePool<String, AbstractWork>();

    private String name;
    private int maxQueueSize;

    public OrderedQueuePoolExecutor(String name, int corePoolSize,
                                    int maxQueueSize) {
        super(corePoolSize, 2 * corePoolSize, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        this.name = name;
        this.maxQueueSize = maxQueueSize;
    }

    public OrderedQueuePoolExecutor(int corePoolSize) {
        this("queue-pool", corePoolSize, 10000);
    }

    /**
     * 增加执行任务
     * @param key
     * @param value
     * @return
     */
    public boolean addTask(String key, AbstractWork task) {
        TasksQueue<AbstractWork> queue = pool.getTasksQueue(key);
        boolean run = false;
        boolean result = false;
        synchronized (queue) {
            if (maxQueueSize > 0) {
                if (queue.size() > maxQueueSize) {
                    logger.error("队列" + name + "(" + key + ")" + "抛弃指令!");
                    queue.clear();
                }
            }
            result = queue.add(task);
            if (result) {
                task.setTasksQueue(queue);
                {
                    if (queue.isProcessingCompleted()) {
                        queue.setProcessingCompleted(false);
                        run = true;
                    }
                }
            } else {
                logger.error("队列添加任务失败");
            }
        }
        if (run) {
            execute(queue.poll());
        }
        return result;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);

        AbstractWork work = (AbstractWork) r;
        TasksQueue<AbstractWork> queue = work.getTasksQueue();
        if (queue != null) {
            AbstractWork afterWork = null;
            synchronized (queue) {
                afterWork = queue.poll();
                if (afterWork == null) {
                    queue.setProcessingCompleted(true);
                }
            }
            if (afterWork != null) {
                execute(afterWork);
            }
        } else {
            logger.error("执行队列为空");
        }
    }

}
