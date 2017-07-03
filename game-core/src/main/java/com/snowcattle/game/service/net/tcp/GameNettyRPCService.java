package com.snowcattle.game.service.net.tcp;

import io.netty.channel.ChannelInitializer;

/**
 * Created by jwp on 2017/3/7.
 * 增加rpc服务
 */
public class GameNettyRPCService extends AbstractNettyTcpServerService{

    public GameNettyRPCService(String serviceId, int serverPort, String bossTreadName, String workThreadName, ChannelInitializer channelInitializer) {
        super(serviceId, serverPort, bossTreadName, workThreadName, channelInitializer);
    }

    @Override
    public boolean startService() throws Exception{
        boolean flag = super.startService();
        return flag;
    }

    @Override
    public boolean stopService() throws Exception{
        boolean flag = super.stopService();
        return flag;
    }
}
