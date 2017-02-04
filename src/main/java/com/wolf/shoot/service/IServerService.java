package com.wolf.shoot.service;

import com.wolf.shoot.exception.IServerServiceException;

/**
 * Created by jwp on 2017/2/4.
 * 服务器启动服务
 */
public interface IServerService {

    String getServiceId();

    boolean initialize();

    boolean startService();
    boolean stopService();

    void release();

    byte getState();
    boolean isRunning();
}
