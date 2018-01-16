package com.snowcattle.game.service.check;

import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.service.IService;
import com.snowcattle.timer.WheelTimer;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2018/1/16.
 * 用于生命周期检查,使用wheeltimer
 */
@Service
public class LifeCycleCheckService implements IService {

    private WheelTimer<Integer> wheelTimer;

    private boolean openFlag = false;
    @Override
    public String getId() {
        return ServiceName.LifeCycleCheckService;
    }

    @Override
    public void startup() throws Exception {
        if(openFlag) {
            wheelTimer = new WheelTimer<Integer>(GlobalConstants.WheelTimer.tickDuration, GlobalConstants.WheelTimer.timeUnit, GlobalConstants.WheelTimer.ticksPerWheel);
            wheelTimer.start();
        }
    }

    @Override
    public void shutdown() throws Exception {
        if(openFlag) {
            wheelTimer.stop();
        }
    }
}
