package com.snowcattle.game.service.proxy;

import com.snowcattle.game.service.net.AbstractNettyTcpServerService;
import io.netty.channel.ChannelInitializer;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2017/6/9.
 * 网络代理服务，用于支持网关
 */
@Service
public class ProxyTcpServer extends AbstractNettyTcpServerService{

    public ProxyTcpServer(String serviceId, int serverPort, String bossTreadName, String workThreadName, ChannelInitializer channelInitializer) {
        super(serviceId, serverPort, bossTreadName, workThreadName, channelInitializer);
    }
}
