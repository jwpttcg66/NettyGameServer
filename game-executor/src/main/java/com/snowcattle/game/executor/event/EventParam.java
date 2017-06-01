package com.snowcattle.game.executor.event;

/**
 * Created by jiangwenping on 17/1/6.
 * 事件参数
 */
public class EventParam<T> {

    private T t;

    public EventParam(T t) {
        this.t = t;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
