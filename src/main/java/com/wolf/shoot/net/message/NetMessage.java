package com.wolf.shoot.net.message;

import io.netty.util.ByteProcessor;

/**
 * Created by jwp on 2017/1/24.
 *  网络基本消息
 */
public class NetMessage {

    private NetMessageHead netMessageHead;
    private NetMessageBody netMessageBody;

    public NetMessageHead getNetMessageHead() {
        return netMessageHead;
    }

    public void setNetMessageHead(NetMessageHead netMessageHead) {
        this.netMessageHead = netMessageHead;
    }

    public NetMessageBody getNetMessageBody() {
        return netMessageBody;
    }

    public void setNetMessageBody(NetMessageBody netMessageBody) {
        this.netMessageBody = netMessageBody;
    }
}
