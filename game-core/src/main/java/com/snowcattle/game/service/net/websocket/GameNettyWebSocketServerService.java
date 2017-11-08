package com.snowcattle.game.service.net.websocket;

import io.netty.channel.ChannelInitializer;

/**
 * Created by jiangwenping on 2017/11/8.
 * websocket服务
 */
public class GameNettyWebSocketServerService extends AbstractNettyWebSocketServerService{
    public GameNettyWebSocketServerService(String serviceId, int serverPort, String bossTreadName, String workThreadName, ChannelInitializer channelInitializer) {
        super(serviceId, serverPort, bossTreadName, workThreadName, channelInitializer);
    }
}
