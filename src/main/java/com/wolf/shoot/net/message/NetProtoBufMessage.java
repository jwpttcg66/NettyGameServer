package com.wolf.shoot.net.message;

/**
 * Created by jiangwenping on 17/2/3.
 */
public class NetProtoBufMessage {

    private NetMessageHead netMessageHead;
    private NetProtoBufMessageBody netMessageBody;

    public NetMessageHead getNetMessageHead() {
        return netMessageHead;
    }

    public void setNetMessageHead(NetMessageHead netMessageHead) {
        this.netMessageHead = netMessageHead;
    }

    public NetProtoBufMessageBody getNetMessageBody() {
        return netMessageBody;
    }

    public void setNetMessageBody(NetProtoBufMessageBody netMessageBody) {
        this.netMessageBody = netMessageBody;
    }
}
