package com.snowcattle.game.service.net.http;

import io.netty.channel.ChannelInitializer;

/**
 * Created by jiangwenping on 2017/7/3.
 * http服务
 */
public class GameNettyHttpServerService extends AbstractNettyHttpServerService{
    public GameNettyHttpServerService(String serviceId, int serverPort, String bossTreadName, String workThreadName, ChannelInitializer channelInitializer) {
        super(serviceId, serverPort, bossTreadName, workThreadName, channelInitializer);
    }
}
