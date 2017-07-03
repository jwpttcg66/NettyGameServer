package com.snowcattle.game.service.net.tcp.process;

import com.snowcattle.game.service.message.AbstractNetMessage;

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
    public void put(AbstractNetMessage msg);

    /**
     * 判断队列是否已经达到上限了
     * @return
     */
    public boolean isFull();
}
