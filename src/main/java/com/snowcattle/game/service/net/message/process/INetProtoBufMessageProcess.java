package com.snowcattle.game.service.net.message.process;

import com.snowcattle.game.service.net.message.AbstractNetMessage;

/**
 * Created by jwp on 2017/2/9.
 */
public interface INetProtoBufMessageProcess {
    public void processNetMessage();
    public void addNetMessage(AbstractNetMessage abstractNetMessage);
    public void close();
}
