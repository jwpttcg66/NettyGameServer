package com.snowcattle.game.service.net.tcp;

import com.snowcattle.game.service.IServerService;
import com.snowcattle.game.bootstrap.manager.ServerServiceManager;

/**
 * 抽象服务基类
 *
 */
public abstract class AbstractServerService implements IServerService {

    private final String serviceId;
    protected byte serviceState;

    public AbstractServerService(String serviceId) {
        this.serviceId = serviceId;
    }

    /* (non-Javadoc)
     * @see com.pwrd.core.service.IService#getServiceId()
     */
    @Override
    public final String getServiceId() {
        return serviceId;
    }

    @Override
    public boolean initialize() {
        ServerServiceManager.getInstance().registerService(serviceId, this);
        return true;
    }
    @Override
    public void release() {
        //从全局服务管理器移除自己
        ServerServiceManager.getInstance().removeService(serviceId);
    }

    @Override
    public boolean startService() throws Exception{
        return true;
    }

    @Override
    public boolean stopService() throws Exception{
        return true;
    }

    @Override
    public final byte getState() {
        return serviceState;
    }

    @Override
    public final boolean isRunning() {
        return true;
    }
}