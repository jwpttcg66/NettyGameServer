package com.wolf.shoot.service;

import java.net.InetSocketAddress;

/**
 * Created by jwp on 2017/2/4.
 */
public class AbstractUdpServerService extends  AbstractServerService{
    protected int serverPort;
    protected InetSocketAddress serverAddress;

    public AbstractUdpServerService(String serviceId) {
        super(serviceId);
    }

    public AbstractUdpServerService(String serviceId, int serverPort, InetSocketAddress serverAddress) {
        super(serviceId);
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
    }


}
