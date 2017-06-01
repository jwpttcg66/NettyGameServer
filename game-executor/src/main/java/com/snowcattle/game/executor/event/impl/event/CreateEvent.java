package com.snowcattle.game.executor.event.impl.event;

import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.EventType;
import com.snowcattle.game.executor.common.utils.Loggers;

import java.io.Serializable;

/**
 * Created by jiangwenping on 17/1/11.
 *  dispatch thread使用
 */
public class CreateEvent<ID extends Serializable> extends CycleEvent {

    public CreateEvent(EventType eventType,ID eventId,  EventParam... parms){
//        setEventType(eventType);
//        setParams(parms);
        super(eventType, eventId, parms);
    }

    public void call() {
        if(Loggers.gameExecutorUtil.isDebugEnabled()){
            EventParam[] eventParams = getParams();
            Loggers.gameExecutorUtil.debug("create event " + eventParams[0].getT());
        }

    }
}
