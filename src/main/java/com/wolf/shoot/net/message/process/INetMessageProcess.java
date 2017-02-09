package com.wolf.shoot.net.message.process;

import com.wolf.shoot.net.message.NetMessage;

/**
 * Created by jwp on 2017/2/9.
 * 消息处理器
 */
public interface INetMessageProcess {
    public void processNetMessage(NetMessage netMessage);
}
