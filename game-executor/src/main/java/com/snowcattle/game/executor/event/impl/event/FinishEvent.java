package com.snowcattle.game.executor.event.impl.event;

import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.EventType;

import java.io.Serializable;

/**
 * Created by jiangwenping on 17/1/11.
 * dispatch 使用
 */
public class FinishEvent <ID extends Serializable> extends CycleEvent {
    public FinishEvent(EventType eventType, ID eventId,EventParam... parms){
//        setEventType(eventType);
//        setParams(parms);
        super(eventType, eventId, parms);
    }

    public void call() {
//        EventParam[] eventParams = getParams();
//        System.out.println(eventParams[0].getT() + "float"+ eventParams[1].getT());
    }
}
