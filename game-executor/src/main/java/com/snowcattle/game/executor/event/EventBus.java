package com.snowcattle.game.executor.event;

import com.snowcattle.game.executor.update.entity.IUpdate;
import com.snowcattle.game.executor.event.common.IEvent;
import com.snowcattle.game.executor.event.common.IEventBus;
import com.snowcattle.game.executor.event.common.IEventListener;
import com.snowcattle.game.executor.event.common.constant.EventTypeEnum;
import com.snowcattle.game.executor.common.utils.Loggers;

import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jiangwenping on 17/1/9.
 */
public class EventBus implements IEventBus {

    private Map<EventType, Set<AbstractEventListener>> listenerMap;

    private Queue<IEvent> events;

    //调用线程size比较费性能，这里采用原子的更新器
    private AtomicInteger size = new AtomicInteger();
    public EventBus() {
        this.listenerMap = new ConcurrentHashMap<EventType, Set<AbstractEventListener>>();
        this.events = new ConcurrentLinkedQueue<IEvent>();
    }

    public void addEventListener(AbstractEventListener listener) {
        Set<EventType> sets = listener.getSet();
        for (EventType eventType: sets){
            if(!listenerMap.containsKey(eventType)){
                listenerMap.put(eventType, new HashSet<AbstractEventListener>());
            }
            listenerMap.get(eventType).add(listener);
        }
    }

    public void removeEventListener(AbstractEventListener abstractEventListener)   {
        Set<EventType> sets = abstractEventListener.getSet();
        for (EventType eventType: sets){
            listenerMap.get(eventType).remove(abstractEventListener);
        }
    }

    public void clearEventListener() {
        listenerMap.clear();
    }

    public void addEvent(IEvent event) {
        this.events.add(event);
        size.getAndIncrement();
    }

    public IEvent pollEvent(){
        IEvent event = events.poll();
        if(event != null){
            size.getAndDecrement();
        }
        return event;
    }
    public void handleEvent() {
        while (!events.isEmpty()){
            IEvent event = pollEvent();
            if(event == null){
                break;
            }
            try {
                handleSingleEvent(event);
            }catch (Exception e){
                Loggers.gameExecutorError.error(e.toString(), e);
            }

        }
    }

    /**
     *单次超过最大设置需要停止
     * 并且返回调度了多少事件
     * @param maxSize
     */
    public int cycle(int maxSize) {
        int i = 0;
        while (!events.isEmpty()){
            IEvent event = pollEvent();
            if(event == null){
                break;
            }
            try {
                handleSingleEvent(event);
            }catch (Exception e){
                Loggers.gameExecutorError.error(e.toString(), e);
            }

            i++;
            if(i > maxSize){
                break;
            }
        }

        return i;
    }

    public void handleSingleEvent(IEvent event) throws Exception{

        if(Loggers.gameExecutorUtil.isDebugEnabled()) {
            EventParam[] eventParams = event.getParams();
            if(eventParams != null) {
                if (eventParams[0].getT() instanceof IUpdate) {
                    IUpdate iUpdate = (IUpdate) eventParams[0].getT();
                    if(event.getEventType().getIndex()< EventTypeEnum.values().length) {
                        Loggers.gameExecutorUtil.debug("handle " + EventTypeEnum.values()[event.getEventType().getIndex()] + " id " + iUpdate.getUpdateId() + " dispatch");
                    }else{
                        Loggers.gameExecutorUtil.debug("handle event type " + event.getEventType().getIndex() + " id " + iUpdate.getUpdateId() + " dispatch");
                    }
                }
            }
        }

        EventType eventType = event.getEventType();
        if(listenerMap.containsKey(eventType)){
            Set<AbstractEventListener> listenerSet = listenerMap.get(eventType);
            for(IEventListener eventListener:listenerSet){
                if(eventListener.containEventType(event.getEventType())) {
                    eventListener.fireEvent(event);
                }
            }
        }
    }

    public void clearEvent() {
        events.clear();
    }

    public void clear() {
        clearEvent();
        clearEventListener();
    }

    /**
     * 获取事件的大小
     * @return
     */
    public int getEventsSize(){
        return size.get();
    }
}
