package com.wolf.shoot.service.net;

import io.netty.channel.ChannelInitializer;

import java.net.InetSocketAddress;

/**
 * Created by jwp on 2017/2/17.
 * udp启动服务
 */
public class GameNettyUdpServerService extends AbstractNettyUdpServerService{
    public GameNettyUdpServerService(String serviceId, int serverPort, String threadNameFactoryName, ChannelInitializer channelInitializer) {
        super(serviceId, serverPort, threadNameFactoryName, channelInitializer);
    }
}
