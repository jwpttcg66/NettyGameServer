package com.wolf.shoot.service.jmx;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Created by jwp on 2017/3/8.
 */
@ManagedResource
public class ThreadPoolStatus {
    private int poolSize;
    private int activeCount;
    private int corePoolSize;
    private int maximumPoolSize;
    private int largestPoolSize;
    private long taskCount;
    private long completedTaskCount;

    @ManagedOperation
    public int getPoolSize() {
        return poolSize;
    }

    @ManagedOperation
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    @ManagedOperation
    public int getActiveCount() {
        return activeCount;
    }

    @ManagedOperation
    public void setActiveCount(int activeCount) {
        this.activeCount = activeCount;
    }

    @ManagedOperation
    public int getCorePoolSize() {
        return corePoolSize;
    }

    @ManagedOperation
    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    @ManagedOperation
    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    @ManagedOperation
    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    @ManagedOperation
    public int getLargestPoolSize() {
        return largestPoolSize;
    }

    @ManagedOperation
    public void setLargestPoolSize(int largestPoolSize) {
        this.largestPoolSize = largestPoolSize;
    }

    @ManagedOperation
    public long getTaskCount() {
        return taskCount;
    }

    @ManagedOperation
    public void setTaskCount(long taskCount) {
        this.taskCount = taskCount;
    }

    @ManagedOperation
    public long getCompletedTaskCount() {
        return completedTaskCount;
    }

    @ManagedOperation
    public void setCompletedTaskCount(long completedTaskCount) {
        this.completedTaskCount = completedTaskCount;
    }
}