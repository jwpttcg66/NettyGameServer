package com.snowcattle.game.executor.event.aysnc;

import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.SingleEvent;
import com.snowcattle.game.executor.event.service.AsyncEventService;

/**
 * Created by jwp on 2017/5/5.
 */
public class AsyncEventServiceTest {
    public static void main(String[] args) throws Exception {
        EventBus eventBus = new EventBus();

        eventBus.addEventListener(new SingleRunEventListener());
        AsyncEventService asyncEventService = new AsyncEventService(eventBus, Short.MAX_VALUE, 2, "async_worker", 20, "async_event_handler", Short.MAX_VALUE);
        asyncEventService.startUp();

        int size = 1000000;
        for(int i = 0; i < size; i++){
            SingleRunEvent singleRunEvent = new SingleRunEvent(TestConstants.singleRunEventType, i, i, null);
            singleRunEvent.setRunId(i);
            asyncEventService.put(singleRunEvent);
        }
    }
}
