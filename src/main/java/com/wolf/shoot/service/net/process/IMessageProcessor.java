package com.wolf.shoot.service.net.process;

import com.wolf.shoot.net.message.NetMessage;

/**
 * Created by jiangwenping on 17/2/13.
 */
public interface IMessageProcessor {
    /**
     * 启动消息处理器
     */
    public void start();

    /**
     * 停止消息处理器
     */
    public void stop();

    /**
     * 向消息队列投递消息
     *
     * @param msg
     */
    public void put(NetMessage msg);

    /**
     * 判断队列是否已经达到上限了
     * @return
     */
    public boolean isFull();
}
