package com.snowcattle.game.service.event.impl;

import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.EventType;
import com.snowcattle.game.executor.event.SingleEvent;
import com.snowcattle.game.service.event.SingleEventConstants;

/**
 * Created by jiangwenping on 2017/5/22.
 * 网络链接断开
 */
public class SessionUnRegisterEvent extends SingleEvent<Long> {
    public SessionUnRegisterEvent( Long eventId, long shardingId, EventParam... parms) {
        super(SingleEventConstants.sessionUnRegister, eventId, shardingId, parms);
    }
}
