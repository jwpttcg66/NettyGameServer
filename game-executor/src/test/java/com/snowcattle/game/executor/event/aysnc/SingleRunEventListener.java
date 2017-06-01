package com.snowcattle.game.executor.event.aysnc;

import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.event.AbstractEventListener;

/**
 * Created by jwp on 2017/5/5.
 */
public class SingleRunEventListener extends AbstractEventListener {
    @Override
    public void initEventType() {
        register(TestConstants.singleRunEventType);
    }
}
