package com.wolf.shoot.service.net.message;

import java.net.InetSocketAddress;

/**
 * Created by jwp on 2017/2/16.
 * 抽象的udp消息
 */
public abstract class AbstractNetProtoBufUdpMessage extends AbstractNetProtoBufMessage {
    /**
     * 发送方
     */
    private InetSocketAddress send;
    /**
     * 接收方
     */
    private InetSocketAddress receive;

//    /**
//     * 协议头
//     */
//    private NetUdpMessageHead netUdpMessageHead;

    public InetSocketAddress getSend() {
        return send;
    }

    public void setSend(InetSocketAddress send) {
        this.send = send;
    }

    public InetSocketAddress getReceive() {
        return receive;
    }

    public void setReceive(InetSocketAddress receive) {
        this.receive = receive;
    }

    public AbstractNetProtoBufUdpMessage(){
        super();
        setNetMessageHead(new NetUdpMessageHead());
        setNetMessageBody(new NetProtoBufMessageBody());
    }

//    public NetUdpMessageHead getNetUdpMessageHead() {
//        return netUdpMessageHead;
//    }
//
//    public void setNetUdpMessageHead(NetUdpMessageHead netUdpMessageHead) {
//        this.netUdpMessageHead = netUdpMessageHead;
//    }
}
