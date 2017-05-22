package com.snowcattle.game.service.event;

import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.common.loader.scanner.ClassScanner;
import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.service.AsyncEventService;
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

    private AsyncEventService asyncEventService;
    @Override
    public String getId() {
        return ServiceName.GameAsyncEventService;
    }

    @Override
    public void startup() throws Exception {
        EventBus eventBus = new EventBus();

//        eventBus.addEventListener(new SingleRunEventListener());
        int eventQueueSize = Short.MAX_VALUE;
        int workSize = 2;
        String queueWorkTheadName = GlobalConstants.Thread.EVENT_WORKER;
        int handleSize = 20;
        String workerHanlderName = GlobalConstants.Thread.EVENT_HANDLER;
        int handleQueueSize =  Short.MAX_VALUE;
        asyncEventService = new AsyncEventService(eventBus, eventQueueSize, workSize, queueWorkTheadName, handleSize, workerHanlderName, handleQueueSize);
        asyncEventService.startUp();
    }

    @Override
    public void shutdown() throws Exception {
        asyncEventService.shutDown();
    }
}
