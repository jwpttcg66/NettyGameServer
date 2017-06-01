package com.snowcattle.game.executor.event;

import com.snowcattle.game.executor.event.common.IEvent;
import com.snowcattle.game.executor.event.common.IEventListener;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by jiangwenping on 17/1/6.
 */
public abstract class AbstractEventListener implements IEventListener {

    private Set<EventType> set;

    public AbstractEventListener() {
        this.set = new CopyOnWriteArraySet<EventType>();
        initEventType();
    }

    public abstract  void initEventType();
    public void register(EventType eventType) {
        this.set.add(eventType);
    }

    public void unRegister(EventType eventType) {
        this.set.remove(eventType);
    }

    public boolean containEventType(EventType eventType) {
        return set.contains(eventType);
    }

    public void fireEvent(IEvent event) {
        event.call();
    }

    public Set<EventType> getSet() {
        return set;
    }

}
