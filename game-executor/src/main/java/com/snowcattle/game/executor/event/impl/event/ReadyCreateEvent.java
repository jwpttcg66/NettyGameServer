package com.snowcattle.game.executor.event.impl.event;

import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.EventType;

/**
 * Created by jiangwenping on 17/1/16.
 * updateService使用
 */
public class ReadyCreateEvent extends CycleEvent {

    public ReadyCreateEvent(EventType eventType, long eventId, EventParam... parms){
        super(eventType, eventId, parms);
    }

    public void call() {

    }
}
