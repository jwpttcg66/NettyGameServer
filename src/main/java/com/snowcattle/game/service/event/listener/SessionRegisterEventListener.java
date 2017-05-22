package com.snowcattle.game.service.event.listener;

import com.snowcattle.game.common.annotation.GlobalEventListenerAnnotation;
import com.snowcattle.game.executor.event.AbstractEventListener;
import com.snowcattle.game.service.event.SingleEventConstants;

/**
 * Created by jiangwenping on 2017/5/22.
 */
@GlobalEventListenerAnnotation
public class SessionRegisterEventListener extends AbstractEventListener {

    @Override
    public void initEventType() {
        register(SingleEventConstants.sessionRegister);
    }
}
