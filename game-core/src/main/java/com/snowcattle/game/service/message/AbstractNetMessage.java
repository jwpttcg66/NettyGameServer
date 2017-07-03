package com.snowcattle.game.service.message;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jwp on 2017/1/24.
 *  网络基本消息
 */
public abstract class AbstractNetMessage implements INetMessage{

    private NetMessageHead netMessageHead;
    private NetMessageBody netMessageBody;

    /**
     * 增加默认属性(附带逻辑调用需要的属性)
     */
    private final ConcurrentHashMap<Object, Object> attributes = new ConcurrentHashMap<Object, Object>(3);

    public NetMessageHead getNetMessageHead() {
        return netMessageHead;
    }

    public void setNetMessageHead(NetMessageHead netMessageHead) {
        this.netMessageHead = netMessageHead;
    }

    public NetMessageBody getNetMessageBody() {
        return netMessageBody;
    }

    public void setNetMessageBody(NetMessageBody netMessageBody) {
        this.netMessageBody = netMessageBody;
    }

    public int getSerial(){
        return netMessageHead.getSerial();
    }

    /**
     * 逻辑处理时候附带的参数
     * @param key
     * @param value
     * @return
     */
    public Object setAttribute(Object key, Object value){
        return attributes.put(key, value);
    }

    public Object getAttribute(Object key){
        return attributes.get(key);
    }

    public void removeAttribute(Object key){
        this.attributes.remove(key);
    }
    public int getCmd(){
        return getNetMessageHead().getCmd();
    }
}
