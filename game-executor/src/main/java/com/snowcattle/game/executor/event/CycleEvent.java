package com.snowcattle.game.executor.event;

import java.io.Serializable;

/**
 * Created by jiangwenping on 17/1/16.
 * 具有生存周期，循环调度的事件
 */
public class CycleEvent<ID extends Serializable>  extends AbstractEvent<ID>  {

    //是否进行过初始化
    private boolean initFlag;

    //使用的更新器的索引
    private int updateExcutorIndex;

    //对象是否存活
    private boolean updateAliveFlag;

    public CycleEvent(){

    }

    public CycleEvent(EventType eventType, ID eventId, EventParam... parms){
        setEventType(eventType);
        setParams(parms);
        setId(eventId);
    }

    @Override
    public void call() {

    }

    public boolean isInitFlag() {
        return initFlag;
    }

    public void setInitFlag(boolean initFlag) {
        this.initFlag = initFlag;
    }

    public int getUpdateExcutorIndex() {
        return updateExcutorIndex;
    }

    public void setUpdateExcutorIndex(int updateExcutorIndex) {
        this.updateExcutorIndex = updateExcutorIndex;
    }

    public boolean isUpdateAliveFlag() {
        return updateAliveFlag;
    }

    public void setUpdateAliveFlag(boolean updateAliveFlag) {
        this.updateAliveFlag = updateAliveFlag;
    }
}
