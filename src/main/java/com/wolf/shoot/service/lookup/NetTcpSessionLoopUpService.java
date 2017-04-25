package com.wolf.shoot.service.lookup;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.service.net.session.NettySession;
import com.wolf.shoot.service.net.session.NettyTcpSession;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jwp on 2017/2/10.
 * session提供服务
 */
@Service
public class NetTcpSessionLoopUpService implements IChannleLookUpService {

    protected static final Logger log = Loggers.serverStatusStatistics;

    protected ConcurrentHashMap<Long, NettySession> sessions = new ConcurrentHashMap<Long, NettySession>();

    @Override
    public NettySession lookup(long channelId) {
        return sessions.get(channelId);
    }

    @Override
    public void addNettySession(NettyTcpSession nettyTcpSession) {
        if(log.isDebugEnabled()){
            log.debug("add nettySesioin " + nettyTcpSession.getChannel().id().asLongText() + " sessionId " + nettyTcpSession.getSessionId());
        }
        sessions.put(nettyTcpSession.getSessionId(), nettyTcpSession);
    }

    @Override
    public boolean removeNettySession(NettyTcpSession nettyTcpSession) {
        if(log.isDebugEnabled()){
            log.debug("remove nettySesioin " + nettyTcpSession.getChannel().id().asLongText() + " sessionId " + nettyTcpSession.getSessionId());
        }
        return sessions.remove(nettyTcpSession.getSessionId()) != null;
    }
}
