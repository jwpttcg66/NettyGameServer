package com.snowcattle.game.service.event.impl;

import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.EventType;
import com.snowcattle.game.executor.event.SingleEvent;

/**
 * Created by jiangwenping on 2017/5/22.
 * 网络链接断开
 */
public class SessionUnRegisterEvent extends SingleEvent<Long> {
    public SessionUnRegisterEvent(EventType eventType, Long eventId, long shardingId, EventParam... parms) {
        super(eventType, eventId, shardingId, parms);
    }
}
