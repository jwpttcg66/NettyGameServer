package com.wolf.shoot.service.net.session;

import com.wolf.shoot.common.IUpdatable;
import com.wolf.shoot.common.exception.NetMessageException;
import com.wolf.shoot.common.uuid.ClientSessionIdGenerator;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.net.message.AbstractNetMessage;
import com.wolf.shoot.service.net.message.process.NetProtoBufMessageProcess;
import io.netty.channel.Channel;

/**
 * Created by jwp on 2017/2/9.
 * netty tcp的session
 */
public class NettyTcpSession extends NettySession implements IUpdatable {

    private long sessionId;

    /**
     * 消息发送
     */
    private NettyTcpNetMessageSender nettyTcpNetMessageSender;

    /**
     * 消息处理器
     */
    private NetProtoBufMessageProcess netProtoBufMessageProcess;

    /**
     * 网络状态检查
     */
    private TcpNetStateUpdate tcpNetStateUpdate;

    public NettyTcpSession(Channel channel) {
        super(channel);
        ClientSessionIdGenerator clientSessionIdGenerator = LocalMananger.getInstance().get(ClientSessionIdGenerator.class);
        sessionId = clientSessionIdGenerator.generateSessionId();
        nettyTcpNetMessageSender = new NettyTcpNetMessageSender(this);
        netProtoBufMessageProcess = new NetProtoBufMessageProcess(this);
        tcpNetStateUpdate = new TcpNetStateUpdate();
    }

    public NettyTcpNetMessageSender getNettyTcpNetMessageSender() {
        return nettyTcpNetMessageSender;
    }

    public void setNettyTcpNetMessageSender(NettyTcpNetMessageSender nettyTcpNetMessageSender) {
        this.nettyTcpNetMessageSender = nettyTcpNetMessageSender;
    }

    public NetProtoBufMessageProcess getNetProtoBufMessageProcess() {
        return netProtoBufMessageProcess;
    }

    public void setNetProtoBufMessageProcess(NetProtoBufMessageProcess netProtoBufMessageProcess) {
        this.netProtoBufMessageProcess = netProtoBufMessageProcess;
    }

    @Override
    public boolean update() {
        netProtoBufMessageProcess.update();
        tcpNetStateUpdate.update();
        return false;
    }

    public void addNetMessage(AbstractNetMessage abstractNetMessage){
        this.netProtoBufMessageProcess.addNetMessage(abstractNetMessage);
    }

    public long getSessionId() {
        return sessionId;
    }

    public TcpNetStateUpdate getTcpNetStateUpdate() {
        return tcpNetStateUpdate;
    }

    public void setTcpNetStateUpdate(TcpNetStateUpdate tcpNetStateUpdate) {
        this.tcpNetStateUpdate = tcpNetStateUpdate;
    }

    public void close() throws NetMessageException {
        //设置网络状态
        this.tcpNetStateUpdate.setDisconnecting();
        this.netProtoBufMessageProcess.close();
        this.nettyTcpNetMessageSender.close();
    }
}
