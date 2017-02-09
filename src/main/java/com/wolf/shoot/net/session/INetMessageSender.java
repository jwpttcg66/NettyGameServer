package com.wolf.shoot.net.session;

import com.wolf.shoot.common.exception.NetMessageException;
import com.wolf.shoot.net.message.NetMessage;
import com.wolf.shoot.net.message.NetProtoBufMessage;

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
    public boolean sendMessage(NetMessage message) throws NetMessageException;

    /**
     * 关闭
     */
    public void close() throws  NetMessageException;
}
