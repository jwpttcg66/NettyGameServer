package com.snowcattle.game.service.lookup;

import com.snowcattle.game.service.net.tcp.session.NettySession;
import com.snowcattle.game.service.net.tcp.session.NettyTcpSession;

/**
 * Created by jiangwenping on 17/2/13.
 */
public interface IChannleLookUpService {

    /**
     * 查找
     * @param sessionId
     * @return
     */
    public NettySession lookup(long sessionId);

    /**
     * 增加
     * @param nettyTcpSession
     */
    public boolean addNettySession(NettyTcpSession nettyTcpSession);

    /**
     * 移除
     * @param nettyTcpSession
     * @return
     */
    public boolean removeNettySession(NettyTcpSession nettyTcpSession);
}
