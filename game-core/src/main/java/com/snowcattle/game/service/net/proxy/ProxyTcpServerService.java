package com.snowcattle.game.service.net.proxy;

import com.snowcattle.game.service.net.tcp.AbstractNettyTcpServerService;
import io.netty.channel.ChannelInitializer;

/**
 * Created by jiangwenping on 2017/6/9.
 * 网络代理服务，用于支持网关
 */
public class ProxyTcpServerService extends AbstractNettyTcpServerService{

    public ProxyTcpServerService(String serviceId, int serverPort, String bossTreadName, String workThreadName, ChannelInitializer channelInitializer) {
        super(serviceId, serverPort, bossTreadName, workThreadName, channelInitializer);
    }
}
