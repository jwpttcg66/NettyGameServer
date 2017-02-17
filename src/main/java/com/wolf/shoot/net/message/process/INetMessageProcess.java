package com.wolf.shoot.net.message.process;

import com.wolf.shoot.net.message.AbstractNetMessage;

/**
 * Created by jwp on 2017/2/9.
 * 消息处理器
 */
public interface INetMessageProcess {
    public void processNetMessage(AbstractNetMessage abstractNetMessage);
}
