package com.wolf.shoot.service.update;

import com.snowcattle.game.executor.update.entity.AbstractUpdate;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.service.net.session.NettyTcpSession;
import com.wolf.shoot.service.net.session.TcpNetState;

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
        if(Loggers.utilLogger.isDebugEnabled()){
            Loggers.utilLogger.debug("update session id " + getId());
        }
    }

    @Override
    public Long getId() {
        return nettyTcpSession.getSessionId();
    }

    public void updateAlive(){
        if(nettyTcpSession.getTcpNetStateUpdate().state.equals(TcpNetState.DESTROY)){
            setActive(false);
        }
    }
}
