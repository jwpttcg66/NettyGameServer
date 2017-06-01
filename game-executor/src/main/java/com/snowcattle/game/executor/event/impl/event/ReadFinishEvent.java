package com.snowcattle.game.executor.event.impl.event;

import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.EventType;

/**
 * Created by jiangwenping on 17/1/16.
 * updateService使用
 */
public class ReadFinishEvent extends CycleEvent {

    //是否是内部销毁 内部销毁才会销毁缓存
    private boolean innerUpdateFlag;

    public ReadFinishEvent(EventType eventType, long eventId, EventParam... parms){
        super(eventType, eventId, parms);
    }

    public void call() {

    }

    public boolean isInnerUpdateFlag() {
        return innerUpdateFlag;
    }

    public void setInnerUpdateFlag(boolean innerUpdateFlag) {
        this.innerUpdateFlag = innerUpdateFlag;
    }
}
