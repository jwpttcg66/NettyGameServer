package com.wolf.shoot.service;

/**
 * Created by jwp on 2017/2/4.
 * 服务器启动服务
 */
public interface IServerService {

    public String getServiceId();

    public boolean initialize();

    public boolean startService();
    public boolean stopService();

    public void release();

    public byte getState();
    public boolean isRunning();
}
