package com.wolf.shoot.manager.spring;

import com.wolf.shoot.common.loader.DefaultClassLoader;
import com.wolf.shoot.common.uuid.ClientSessionIdGenerator;
import com.wolf.shoot.logic.net.NetMessageDispatchLogic;
import com.wolf.shoot.logic.net.NetMessageProcessLogic;
import com.wolf.shoot.service.net.session.builder.NettyTcpSessionBuilder;
import com.wolf.shoot.service.net.session.builder.NettyUdpSessionBuilder;
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
    private DefaultClassLoader defaultClassLoader;

    @Autowired
    private NettyTcpSessionBuilder nettyTcpSessionBuilder;

    @Autowired
    private NettyUdpSessionBuilder nettyUdpSessionBuilder;

    @Autowired
    private ClientSessionIdGenerator clientSessionIdGenerator;

    @Autowired
    private NetMessageDispatchLogic netMessageDispatchLogic;

    @Autowired
    private NetMessageProcessLogic netMessageProcessLogic;

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

    public DefaultClassLoader getDefaultClassLoader() {
        return defaultClassLoader;
    }

    public void setDefaultClassLoader(DefaultClassLoader defaultClassLoader) {
        this.defaultClassLoader = defaultClassLoader;
    }

    public ClientSessionIdGenerator getClientSessionIdGenerator() {
        return clientSessionIdGenerator;
    }

    public void setClientSessionIdGenerator(ClientSessionIdGenerator clientSessionIdGenerator) {
        this.clientSessionIdGenerator = clientSessionIdGenerator;
    }

    public NetMessageDispatchLogic getNetMessageDispatchLogic() {
        return netMessageDispatchLogic;
    }

    public void setNetMessageDispatchLogic(NetMessageDispatchLogic netMessageDispatchLogic) {
        this.netMessageDispatchLogic = netMessageDispatchLogic;
    }

    public NetMessageProcessLogic getNetMessageProcessLogic() {
        return netMessageProcessLogic;
    }

    public void setNetMessageProcessLogic(NetMessageProcessLogic netMessageProcessLogic) {
        this.netMessageProcessLogic = netMessageProcessLogic;
    }

    public void start() throws  Exception{
        defaultClassLoader.startup();
    }

    public void stop() throws  Exception{

    }
}
