package com.snowcattle.game.service.message.facade;

import com.snowcattle.game.common.exception.GameHandlerException;
import com.snowcattle.game.service.message.AbstractNetMessage;

/**
 * Created by jiangwenping on 17/2/8.
 */
public interface IFacade {
    public AbstractNetMessage dispatch(AbstractNetMessage message)  throws GameHandlerException;
}
