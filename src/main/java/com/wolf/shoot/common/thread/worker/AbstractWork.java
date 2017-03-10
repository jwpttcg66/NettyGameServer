package com.wolf.shoot.common.thread.worker;

/**
 * Created by jiangwenping on 17/3/10.
 */
public abstract class AbstractWork implements Runnable {

    private TasksQueue<AbstractWork> tasksQueue;

    public TasksQueue<AbstractWork> getTasksQueue() {
        return tasksQueue;
    }

    public void setTasksQueue(TasksQueue<AbstractWork> tasksQueue) {
        this.tasksQueue = tasksQueue;
    }
}