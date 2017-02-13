package com.wolf.shoot.service.lookup;

import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.net.session.NettySession;
import org.slf4j.Logger;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jwp on 2017/2/10.
 * session提供服务
 */
public class NetTcpSessionLoopUpService implements IChannleLookUpService {

    protected static final Logger log = Loggers.serverStatusStatistics;

    protected ConcurrentHashMap<String, NettySession> sessions = new ConcurrentHashMap<String, NettySession>();

    @Override
    public NettySession lookup(String channelId) {
        return sessions.get(channelId);
    }

    @Override
    public void addNettySession(NettySession nettySession) {
        if(log.isDebugEnabled()){
            log.debug("add nettySesioin " + nettySession.getChannel().id().asLongText());
        }
        sessions.put(nettySession.getChannel().id().asLongText(), nettySession);
    }

    @Override
    public boolean removeNettySession(NettySession nettySession) {
        if(log.isDebugEnabled()){
            log.debug("remove nettySesioin " + nettySession.getChannel().id().asLongText());
        }
        return sessions.remove(nettySession.getChannel().id().asLongText()) != null;
    }
}
