package com.snowcattle.game.executor.event.common;

import com.snowcattle.game.executor.event.EventType;

/**
 * Created by jiangwenping on 17/1/6.
 * 事件监听器
 */
public interface IEventListener {

    public void register(EventType eventType);
    public boolean containEventType(EventType eventType);
    public void fireEvent(IEvent event);
}
