package com.snowcattle.game.executor.event.impl.event;

import com.snowcattle.game.executor.event.CycleEvent;
import com.snowcattle.game.executor.event.EventParam;
import com.snowcattle.game.executor.event.EventType;

import java.io.Serializable;

/**
 * Created by jiangwenping on 17/1/11.
 *  disptach线程使用
 */
public class UpdateEvent <ID extends Serializable> extends CycleEvent {
//    //是否进行过初始化
//    private boolean initFlag;
//
//    //使用的更新器的索引
//    private int updateExcutorIndex;
//
//    //对象是否存活
//    private boolean updateAliveFlag;

    public UpdateEvent(){

    }

    public UpdateEvent(EventType eventType, ID eventId, EventParam... parms){
//        setEventType(eventType);
//        setParams(parms);
        super(eventType, eventId, parms);
        setUpdateAliveFlag(true);
    }

    public void call() {
//        if(Loggers.gameExecutorUtil.isDebugEnabled()){
//            EventParam[] eventParams = getParams();
//            Loggers.gameExecutorUtil.debug("update event " + eventParams[0].getT());
//        }
    }

//    public boolean isInitFlag() {
//        return initFlag;
//    }
//
//    public void setInitFlag(boolean initFlag) {
//        this.initFlag = initFlag;
//    }
//
//    public void setUpdateExcutorIndex(int updateExcutorIndex) {
//        this.updateExcutorIndex = updateExcutorIndex;
//    }
//
//    public int getUpdateExcutorIndex() {
//        return updateExcutorIndex;
//    }
//
//    public boolean isUpdateAliveFlag() {
//        return updateAliveFlag;
//    }
//
//    public void setUpdateAliveFlag(boolean updateAliveFlag) {
//        this.updateAliveFlag = updateAliveFlag;
//    }
}
