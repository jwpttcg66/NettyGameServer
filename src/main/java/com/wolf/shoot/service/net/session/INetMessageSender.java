package com.wolf.shoot.service.net.session;

import com.wolf.shoot.common.exception.NetMessageException;
import com.wolf.shoot.service.net.message.AbstractNetMessage;

/**
 * Created by jwp on 2017/2/9.
 * 消息处理器
 */
public interface INetMessageSender {
    /**
     * 发送消息
     * @param message
     * @return
     */
    public boolean sendMessage(AbstractNetMessage abstractNetMessage) throws NetMessageException;

    /**
     * 关闭
     */
    public void close() throws  NetMessageException;
}
