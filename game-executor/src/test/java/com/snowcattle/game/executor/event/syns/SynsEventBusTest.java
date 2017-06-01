package com.snowcattle.game.executor.event.syns;

import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.impl.event.FinishEvent;
import com.snowcattle.game.executor.event.impl.listener.CreateEventListener;
import com.snowcattle.game.executor.event.impl.listener.FinishEventListener;
import com.snowcattle.game.executor.event.impl.listener.UpdateEventListener;
import com.snowcattle.game.executor.common.utils.Constants;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class SynsEventBusTest {
    public static void main(String[] args) {
        testSynsEvent();
    }

    public static void testSynsEvent(){
        EventBus eventBus = new EventBus();
        eventBus.addEventListener(new CreateEventListener());
        eventBus.addEventListener(new UpdateEventListener());
        eventBus.addEventListener(new FinishEventListener());

        int maxSize = 10000;
        for(int i = 0; i < maxSize; i++) {
            EventParam<Integer> intParam = new EventParam<Integer>(1);
            EventParam<Float> floatEventParam = new EventParam<Float>(2.0f);
//            CreateEvent event = new CreateEvent(Constants.EventTypeConstans.createEventType, intParam, floatEventParam);
//            UpdateEvent event = new UpdateEvent(Constants.EventTypeConstans.updateEventType, intParam, floatEventParam);
            long id = i;
            FinishEvent event = new FinishEvent(Constants.EventTypeConstans.finishEventType, id,  intParam, floatEventParam);
            eventBus.addEvent(event);
        }
        long startTime = System.currentTimeMillis();
        eventBus.handleEvent();
        long endTime = System.currentTimeMillis();
        long useTime = endTime - startTime;
        System.out.println("完成个数" + maxSize + "耗时" + useTime);
    }
}
