package com.snowcattle.game.executor.common;

/**
 * Created by jiangwenping on 17/4/25.
 * 更新器执行类型
 */
public enum UpdateExecutorEnum {
    /*使用locksupport方式*/
    locksupport,
    /*使用绑定线程*/
    bindThread,
    /**使用disruptor*/
    disruptor,
    ;
}
