package com.snowcattle.game.service.event;

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.common.loader.scanner.ClassScanner;
import com.snowcattle.game.service.IService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 2017/5/22.
 * 游戏内的事件全局服务
 */
@Service
public class GameAsyncEventService implements IService{

    /**
     * Logger for this class
     */
    public static final Logger logger = Loggers.serverLogger;

    public ClassScanner classScanner = new ClassScanner();

    @Override
    public String getId() {
        return ServiceName.GameAsyncEventService;
    }

    @Override
    public void startup() throws Exception {

    }

    @Override
    public void shutdown() throws Exception {

    }
}
