package com.snowcattle.game.service.net.udp;

import com.snowcattle.game.service.net.udp.AbstractNettyUdpServerService;
import io.netty.channel.ChannelInitializer;

/**
 * Created by jwp on 2017/2/17.
 * udp启动服务
 */
public class GameNettyUdpServerService extends AbstractNettyUdpServerService {
    public GameNettyUdpServerService(String serviceId, int serverPort, String threadNameFactoryName, ChannelInitializer channelInitializer) {
        super(serviceId, serverPort, threadNameFactoryName, channelInitializer);
    }
}
