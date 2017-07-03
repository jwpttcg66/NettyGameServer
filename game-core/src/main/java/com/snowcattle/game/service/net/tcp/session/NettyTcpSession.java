package com.snowcattle.game.service.net.tcp.session;

import com.snowcattle.game.common.IUpdatable;
import com.snowcattle.game.common.exception.NetMessageException;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.message.AbstractNetMessage;
import com.snowcattle.game.service.message.process.NetProtoBufMessageProcess;
import com.snowcattle.game.service.uuid.LongIdGenerator;
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

    /**
     * 网络消息切换开关，当玩家进入房间的时候，关闭开关，所有消息处理放入房间内，保证房间内协议处理单线程
     */
    private volatile boolean netMessageProcessSwitch = true;

    public NettyTcpSession(Channel channel) {
        super(channel);
        LongIdGenerator longIdGenerator = LocalMananger.getInstance().getLocalSpringBeanManager().getLongIdGenerator();
        sessionId = longIdGenerator.generateId();
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
//        netProtoBufMessageProcess.update();
        processNetMessage(false);
        tcpNetStateUpdate.update();
        return false;
    }

    /**
     * 增加消息处理切换。
     * @param switchFlag
     */
    public void processNetMessage(boolean switchFlag){
        if(netMessageProcessSwitch || switchFlag){
            netProtoBufMessageProcess.update();
        }
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

    public boolean isNetMessageProcessSwitch() {
        return netMessageProcessSwitch;
    }

    public void setNetMessageProcessSwitch(boolean netMessageProcessSwitch) {
        this.netMessageProcessSwitch = netMessageProcessSwitch;
    }
}
