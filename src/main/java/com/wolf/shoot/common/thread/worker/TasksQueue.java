package com.wolf.shoot.common.thread.worker;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jiangwenping on 17/3/10.
 */
public class TasksQueue<V> {

    /**
     * 命令队列
     */
    private final BlockingQueue<V> tasksQueue = new LinkedBlockingQueue<V>();

    private boolean processingCompleted = true;

    /**
     * 下一执行命令
     *
     * @return
     */
    public V poll() {
        return tasksQueue.poll();
    }

    /**
     * 增加执行指令
     *
     * @param command
     * @return
     */
    public boolean add(V value) {
        return tasksQueue.add(value);
    }

    /**
     * 清理
     */
    public void clear() {
        tasksQueue.clear();
    }

    /**
     * 获取指令数量
     *
     * @return
     */
    public int size() {
        return tasksQueue.size();
    }

    public boolean isProcessingCompleted() {
        return processingCompleted;
    }

    public void setProcessingCompleted(boolean processingCompleted) {
        this.processingCompleted = processingCompleted;
    }

}
