package com.snowcattle.game.manager.spring;

import com.snowcattle.game.logic.net.NetMessageProcessLogic;
import com.snowcattle.game.service.net.pipeline.DefaultTcpServerPipeLine;
import com.snowcattle.game.service.net.session.builder.NettyTcpSessionBuilder;
import com.snowcattle.game.service.net.session.builder.NettyUdpSessionBuilder;
import com.snowcattle.game.service.uuid.ClientSessionIdGenerator;
import com.snowcattle.game.logic.net.NetMessageTcpDispatchLogic;
import com.snowcattle.game.service.net.message.factory.TcpMessageFactory;
import com.snowcattle.game.service.net.pipeline.DefaultUdpServerPipeLine;
import com.snowcattle.game.service.rpc.client.RpcRequestFactory;
import com.snowcattle.game.service.rpc.serialize.protostuff.ProtostuffSerializeI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by jiangwenping on 17/3/2.
 * 这里的都是单例
 *
 */
@Repository
public class LocalSpringBeanManager {

    @Autowired
    private NettyTcpSessionBuilder nettyTcpSessionBuilder;

    @Autowired
    private NettyUdpSessionBuilder nettyUdpSessionBuilder;

    @Autowired
    private ClientSessionIdGenerator clientSessionIdGenerator;

    @Autowired
    private NetMessageTcpDispatchLogic netMessageTcpDispatchLogic;

    @Autowired
    private NetMessageProcessLogic netMessageProcessLogic;

    @Autowired
    private DefaultTcpServerPipeLine defaultTcpServerPipeLine;

    @Autowired
    private DefaultUdpServerPipeLine defaultUdpServerPipeLine;

    @Autowired
    private TcpMessageFactory tcpMessageFactory;

    @Autowired
    private ProtostuffSerializeI protostuffSerialize;

    @Autowired
    private RpcRequestFactory requestFactory;

    public NettyUdpSessionBuilder getNettyUdpSessionBuilder() {
        return nettyUdpSessionBuilder;
    }

    public void setNettyUdpSessionBuilder(NettyUdpSessionBuilder nettyUdpSessionBuilder) {
        this.nettyUdpSessionBuilder = nettyUdpSessionBuilder;
    }

    public NettyTcpSessionBuilder getNettyTcpSessionBuilder() {
        return nettyTcpSessionBuilder;
    }

    public void setNettyTcpSessionBuilder(NettyTcpSessionBuilder nettyTcpSessionBuilder) {
        this.nettyTcpSessionBuilder = nettyTcpSessionBuilder;
    }

    public ClientSessionIdGenerator getClientSessionIdGenerator() {
        return clientSessionIdGenerator;
    }

    public void setClientSessionIdGenerator(ClientSessionIdGenerator clientSessionIdGenerator) {
        this.clientSessionIdGenerator = clientSessionIdGenerator;
    }

    public NetMessageTcpDispatchLogic getNetMessageTcpDispatchLogic() {
        return netMessageTcpDispatchLogic;
    }

    public void setNetMessageTcpDispatchLogic(NetMessageTcpDispatchLogic netMessageTcpDispatchLogic) {
        this.netMessageTcpDispatchLogic = netMessageTcpDispatchLogic;
    }

    public NetMessageProcessLogic getNetMessageProcessLogic() {
        return netMessageProcessLogic;
    }

    public void setNetMessageProcessLogic(NetMessageProcessLogic netMessageProcessLogic) {
        this.netMessageProcessLogic = netMessageProcessLogic;
    }

    public DefaultTcpServerPipeLine getDefaultTcpServerPipeLine() {
        return defaultTcpServerPipeLine;
    }

    public void setDefaultTcpServerPipeLine(DefaultTcpServerPipeLine defaultTcpServerPipeLine) {
        this.defaultTcpServerPipeLine = defaultTcpServerPipeLine;
    }

    public DefaultUdpServerPipeLine getDefaultUdpServerPipeLine() {
        return defaultUdpServerPipeLine;
    }

    public void setDefaultUdpServerPipeLine(DefaultUdpServerPipeLine defaultUdpServerPipeLine) {
        this.defaultUdpServerPipeLine = defaultUdpServerPipeLine;
    }

    public TcpMessageFactory getTcpMessageFactory() {
        return tcpMessageFactory;
    }

    public void setTcpMessageFactory(TcpMessageFactory tcpMessageFactory) {
        this.tcpMessageFactory = tcpMessageFactory;
    }

    public ProtostuffSerializeI getProtostuffSerialize() {
        return protostuffSerialize;
    }

    public void setProtostuffSerialize(ProtostuffSerializeI protostuffSerialize) {
        this.protostuffSerialize = protostuffSerialize;
    }

    public RpcRequestFactory getRequestFactory() {
        return requestFactory;
    }

    public void setRequestFactory(RpcRequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }
}
