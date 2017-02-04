package com.wolf.shoot.service;

import java.net.InetSocketAddress;

/**
 * Created by jwp on 2017/2/4.
 * 抽象的tcp服务
 */
public class AbstractTcpServerService extends  AbstractServerService{

    protected int serverPort;
    protected InetSocketAddress serverAddress;

    public AbstractTcpServerService(String serviceId) {
        super(serviceId);
    }

    public AbstractTcpServerService(String serviceId, int serverPort, InetSocketAddress serverAddress) {
        super(serviceId);
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
    }
}
