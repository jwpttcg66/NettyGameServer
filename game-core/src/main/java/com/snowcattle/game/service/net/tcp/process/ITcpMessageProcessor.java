package com.snowcattle.game.service.net.tcp.process;

import com.snowcattle.game.service.message.AbstractNetMessage;

/**
 * Created by jiangwenping on 17/2/20.
 */
public interface ITcpMessageProcessor extends IMessageProcessor{

    /**
     * 向消息队列投递消息
     * 直接投递到对象processor上面
     * @param msg
     */
    public void directPutTcpMessage(AbstractNetMessage msg);
}
