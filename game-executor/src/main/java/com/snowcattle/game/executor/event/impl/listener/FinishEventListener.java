package com.snowcattle.game.executor.event.impl.listener;

import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.event.AbstractEventListener;

/**
 * Created by jiangwenping on 17/1/11.
 * 完成监听器
 */
public class FinishEventListener extends AbstractEventListener {

    @Override
    public void initEventType() {
        register(Constants.EventTypeConstans.finishEventType);
    }

}
