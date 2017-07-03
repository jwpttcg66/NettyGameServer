package com.snowcattle.game.service.update;

import com.snowcattle.game.executor.update.entity.AbstractUpdate;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.service.net.tcp.session.NettyTcpSession;
import com.snowcattle.game.service.net.tcp.session.TcpNetState;

/**
 * Created by jiangwenping on 17/2/14.
 */
public class NettyTcpSerssionUpdate extends AbstractUpdate<Long> {

    private NettyTcpSession nettyTcpSession;

    public NettyTcpSerssionUpdate(NettyTcpSession nettyTcpSession) {
        this.nettyTcpSession = nettyTcpSession;
    }

    @Override
    public void update() {
        nettyTcpSession.update();
        updateAlive();
        if(Loggers.sessionLogger.isDebugEnabled()){
            Loggers.sessionLogger.debug("update session update id " + getUpdateId());
        }
    }

    @Override
    public Long getUpdateId() {
        return nettyTcpSession.getSessionId();
    }

    public void updateAlive(){
        if(nettyTcpSession.getTcpNetStateUpdate().state.equals(TcpNetState.DESTROY)){
            setActive(false);
        }
    }
}
