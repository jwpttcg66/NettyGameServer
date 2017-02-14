package com.wolf.shoot.net.message.facade;

import com.wolf.shoot.common.exception.GameHandlerException;
import com.wolf.shoot.net.message.NetMessage;

/**
 * Created by jiangwenping on 17/2/8.
 */
public interface IFacade {
    public NetMessage dispatch(NetMessage message)  throws GameHandlerException;
}
