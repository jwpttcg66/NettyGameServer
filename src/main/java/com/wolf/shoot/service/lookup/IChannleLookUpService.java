package com.wolf.shoot.service.lookup;

import com.wolf.shoot.net.session.NettySession;

/**
 * Created by jiangwenping on 17/2/13.
 */
public interface IChannleLookUpService {

    /**
     * 查找
     * @param Id
     * @return
     */
    public NettySession lookup(String channelId);

    /**
     * 增加
     * @param NettySession
     */
    public void addNettySession(NettySession nettySession);

    /**
     * 移除
     * @param t
     * @return
     */
    public boolean removeNettySession(NettySession nettySession);
}
