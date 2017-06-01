package com.snowcattle.game.executor.event;

import java.io.Serializable;

/**
 * Created by jwp on 2017/5/5.
 * 单次循环的事件
 */
public class SingleEvent<ID extends Serializable> extends AbstractEvent<ID> {

    //用于线程分片的shardingId
    private Long shardingId;

    public SingleEvent(EventType eventType, ID eventId, long shardingId, EventParam... parms){
        setEventType(eventType);
        setParams(parms);
        setId(eventId);
        this.shardingId = shardingId;
    }

    public Long getShardingId() {
        return shardingId;
    }

    public void setShardingId(Long shardingId) {
        this.shardingId = shardingId;
    }

    @Override
    public void call() {

    }
}
