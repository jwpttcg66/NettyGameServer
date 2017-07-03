package com.snowcattle.game.service.net.tcp.session;

import com.snowcattle.game.common.exception.NetMessageException;
import com.snowcattle.game.service.message.AbstractNetMessage;

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
