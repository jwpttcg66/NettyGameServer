package com.wolf.shoot.net.message;

import io.netty.util.ByteProcessor;

/**
 * Created by jwp on 2017/1/24.
 *  网络基本消息
 */
public abstract  class NetMessage {

    private NetMessageHead netMessageHead;
    private NetMessageBody netMessageBody;

    public NetMessage(NetMessageHead netMessageHead, NetMessageBody netMessageBody) {
        this.netMessageHead = netMessageHead;
        this.netMessageBody = netMessageBody;
    }

    public NetMessageHead getNetMessageHead() {
        return netMessageHead;
    }

    public void setNetMessageHead(NetMessageHead netMessageHead) {
        this.netMessageHead = netMessageHead;
    }
}
