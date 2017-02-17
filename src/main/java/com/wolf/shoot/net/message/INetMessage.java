package com.wolf.shoot.net.message;

/**
 * Created by jwp on 2017/2/17.
 * 基础协议
 */
public interface INetMessage {
    public NetMessageHead getNetMessageHead();
    public NetMessageBody getNetMessageBody();
}
