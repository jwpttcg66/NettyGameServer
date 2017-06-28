package com.snowcattle.game.service.limit;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jiangwenping on 2017/6/28.
 * 原子计数器
 */
public class AtomicLimitNumber {

    /**
     * 原子数量
     */
    private AtomicLong atomicLong;

    public AtomicLimitNumber() {
        this.atomicLong = new AtomicLong();
    }

    public long increment(){
        return this.atomicLong.incrementAndGet();
    }

    public long decrement(){
        return this.atomicLong.decrementAndGet();
    }
}
