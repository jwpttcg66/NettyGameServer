package com.wolf.shoot.service.net.message.facade;

import com.wolf.shoot.common.exception.GameHandlerException;
import com.wolf.shoot.service.net.message.AbstractNetMessage;

/**
 * Created by jiangwenping on 17/2/8.
 */
public interface IFacade {
    public AbstractNetMessage dispatch(AbstractNetMessage message)  throws GameHandlerException;
}
