package com.wolf.shoot.service.net;

/**
 * Created by jiangwenping on 17/2/7.
 * 游戏里的的tcp服务
 *
 */
public class GameNettyTcpServerService extends AbstractNettyTcpServerService {

    public GameNettyTcpServerService(String serviceId, int serverPort) {
        super(serviceId, serverPort);
    }
}
