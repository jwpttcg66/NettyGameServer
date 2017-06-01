package com.snowcattle.game.executor.event.aysnc;

import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.EventType;
import com.snowcattle.game.executor.event.SingleEvent;

import java.io.Serializable;

/**
 * Created by jwp on 2017/5/5.
 */
public class SingleRunEvent extends SingleEvent{
    public SingleRunEvent(EventType eventType, Serializable eventId, long shardingId, EventParam[] parms) {
        super(eventType, eventId, shardingId, parms);
    }

    private long runId;

    public long getRunId() {
        return runId;
    }

    public void setRunId(long runId) {
        this.runId = runId;
    }

    @Override
    public void call() {
        runId++;

        System.out.println("runId" + runId + " Id" + getShardingId() );
    }
}
