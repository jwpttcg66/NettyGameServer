package com.wolf.shoot.net.session;

import com.wolf.shoot.net.message.process.NetProtoBufMessageProcess;
import io.netty.channel.Channel;

/**
 * Created by jwp on 2017/2/9.
 * netty tcp的session
 */
public class NettyTcpSession extends NettySession{

    /**
     * 消息发送
     */
    private NettyTcpNetMessageSender nettyTcpNetMessageSender;

    /**
     * 消息处理器
     */
    private NetProtoBufMessageProcess netProtoBufMessageProcess;

    public NettyTcpSession(Channel channel) {
        super(channel);
        nettyTcpNetMessageSender = new NettyTcpNetMessageSender(this);
        netProtoBufMessageProcess = new NetProtoBufMessageProcess(this);
    }


}
