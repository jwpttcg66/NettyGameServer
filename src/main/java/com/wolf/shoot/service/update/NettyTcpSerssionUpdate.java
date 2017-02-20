package com.wolf.shoot.service.update;

import com.snowcattle.game.excutor.update.AbstractUpdate;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.service.net.session.NettyTcpSession;

/**
 * Created by jiangwenping on 17/2/14.
 */
public class NettyTcpSerssionUpdate extends AbstractUpdate{

    private NettyTcpSession nettyTcpSession;

    public NettyTcpSerssionUpdate(NettyTcpSession nettyTcpSession) {
        this.nettyTcpSession = nettyTcpSession;
    }

    @Override
    public void update() {
        nettyTcpSession.update();
        if(Loggers.utilLogger.isDebugEnabled()){
            Loggers.utilLogger.debug("update session id " + getId());
        }
    }

    @Override
    public long getId() {
        return nettyTcpSession.getSessionId();
    }

}
