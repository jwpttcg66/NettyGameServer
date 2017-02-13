package com.wolf.shoot.net.message.process;

import com.wolf.shoot.net.message.NetMessage;

/**
 * Created by jwp on 2017/2/9.
 */
public interface INetProtoBufMessageProcess {
    public void processNetMessage();
    public void addNetMessage(NetMessage netMessage);
}
