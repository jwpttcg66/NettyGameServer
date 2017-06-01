package com.snowcattle.game.executor.event.common;

import com.snowcattle.game.executor.event.AbstractEventListener;

/**
 * Created by jiangwenping on 17/1/6.
 * ⌚
 */
public interface IEventBus {
    public void addEventListener(AbstractEventListener listene);
    public void removeEventListener(AbstractEventListener listene);
    public void clearEventListener();
    public void addEvent(IEvent event);
    public void handleEvent();
    public void handleSingleEvent(IEvent event) throws Exception;
    public void clearEvent();
    public void clear();
}
