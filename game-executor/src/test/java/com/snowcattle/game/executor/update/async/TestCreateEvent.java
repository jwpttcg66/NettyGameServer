package com.snowcattle.game.executor.update.async;

import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.EventType;
import com.snowcattle.game.executor.event.impl.event.CreateEvent;

/**
 * Created by jiangwenping on 17/1/12.
 */
public class TestCreateEvent extends CreateEvent {
    public TestCreateEvent(EventType eventType, EventParam... parms) {
        super(eventType, parms);
    }

    public void call() {
        EventParam[] eventParams = getParams();
        int a = (int) eventParams[0].getT();
        if(a %1000 == 0) {
            System.out.println(eventParams[0].getT() + "float" + eventParams[1].getT());
        }
    }
}
