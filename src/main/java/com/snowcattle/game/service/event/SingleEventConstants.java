package com.snowcattle.game.service.event;

import com.snowcattle.game.executor.event.EventType;

/**
 * Created by jiangwenping on 2017/5/22.
 */
public class SingleEventConstants {
    public static EventType singleRunEventType = new EventType(1001);
    public static EventType sessionRegister = new EventType(1002);
    public static EventType sessionUnRegister = new EventType(1003);
}
