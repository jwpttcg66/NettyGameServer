package com.snowcattle.game.service.net.tcp;

import io.netty.channel.ChannelInitializer;

/**
 * Created by jiangwenping on 17/2/7.
 * 游戏里的的tcp服务
 *
 */
public class GameNettyTcpServerService extends AbstractNettyTcpServerService {

    public GameNettyTcpServerService(String serviceId, int serverPort, String bossThreadName, String workThreadName, ChannelInitializer channelInitializer) {
        super(serviceId, serverPort, bossThreadName, workThreadName, channelInitializer);
    }
}
