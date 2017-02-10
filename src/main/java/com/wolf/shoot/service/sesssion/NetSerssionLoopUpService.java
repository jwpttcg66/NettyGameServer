package com.wolf.shoot.service.sesssion;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.net.session.NettySession;
import com.wolf.shoot.service.ILookUpService;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jwp on 2017/2/10.
 * session提供服务
 */
public class NetSerssionLoopUpService implements ILookUpService<NettySession> {

    protected static final Logger log = Loggers.serverStatusStatistics;

    protected ConcurrentHashMap<Long, NettySession> sessions = new ConcurrentHashMap<Long, NettySession>();
    @Override
    public NettySession lookup(long Id) {
        return null;
    }

    @Override
    public void addT(NettySession nettySession) {
        sessions.put(nettySession.getSessionId(), nettySession);
    }

    @Override
    public boolean removeT(NettySession nettySession) {
        boolean flag = sessions.remove(nettySession.getSessionId(), nettySession);
        return flag;
    }

}
