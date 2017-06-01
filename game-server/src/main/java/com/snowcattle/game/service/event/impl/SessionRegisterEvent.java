package com.snowcattle.game.service.event.impl;

import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.EventType;
import com.snowcattle.game.executor.event.SingleEvent;
import com.snowcattle.game.service.event.SingleEventConstants;

/**
 * Created by jiangwenping on 2017/5/22.
 * 网络链接事件建立
 */
public class SessionRegisterEvent extends SingleEvent<Long>{

    public SessionRegisterEvent(Long eventId, long shardingId, EventParam... parms) {
        super(SingleEventConstants.sessionRegister, eventId, shardingId, parms);
    }

}
