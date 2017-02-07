package com.wolf.shoot.service.net;

import java.net.InetSocketAddress;

/**
 * Created by jiangwenping on 17/2/7.
 * 游戏里的的tcp服务
 *
 */
public class GameNettyTcpServerService extends AbstractNettyTcpServerService {

    public GameNettyTcpServerService(String serviceId, int serverPort, InetSocketAddress serverAddress) {
        super(serviceId, serverPort, serverAddress);
    }
}
