package com.snowcattle.game.executor.event;

import com.snowcattle.game.executor.event.common.IEvent;

import java.io.Serializable;

/**
 * Created by jiangwenping on 17/1/9.
 */
public abstract  class AbstractEvent<ID extends Serializable> implements IEvent {

    private EventType eventType;
    private EventParam[] eventParamps;
    private ID id;

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return this.eventType;
    }

    public EventParam[] getParams() {
        return this.eventParamps;
    }

    public void setParams(EventParam... eventParams) {
        this.eventParamps = eventParams;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
