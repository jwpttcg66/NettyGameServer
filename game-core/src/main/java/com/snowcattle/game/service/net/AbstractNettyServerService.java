package com.snowcattle.game.service.net;

import com.snowcattle.game.service.net.tcp.AbstractServerService;

import java.net.InetSocketAddress;

/**
 * Created by jwp on 2017/2/4.
 * 抽象的tcp服务
 */
public abstract class AbstractNettyServerService extends AbstractServerService {

    protected int serverPort;
    protected InetSocketAddress serverAddress;


    public AbstractNettyServerService(String serviceId, int serverPort) {
        super(serviceId);
        this.serverPort = serverPort;
        this.serverAddress = new InetSocketAddress(serverPort);
    }
}
