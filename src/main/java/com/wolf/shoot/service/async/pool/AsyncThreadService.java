package com.wolf.shoot.service.async.pool;

import com.snowcattle.game.excutor.thread.ThreadNameFactory;
import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.common.util.ExecutorUtil;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.async.AsyncCall;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * Created by jiangwenping on 17/4/19.
 * 增加异步线程池
 */
@Service
public class AsyncThreadService implements AsyncThreadPool, IService {

    protected ExecutorService executorService;


    @Override
    public Future submit(AsyncCall asyncCall) {
        return executorService.submit(asyncCall);
    }

    @Override
    public String getId() {
        return ServiceName.AsyncThreadService;
    }

    @Override
    public void startup() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        ThreadNameFactory threadNameFactory =  new ThreadNameFactory(GlobalConstants.Thread.GAME_ASYNC_CALL);
        executorService = new ThreadPoolExecutor(gameServerConfig.getAsyncThreadPoolCoreSize(), gameServerConfig.getAsyncThreadPoolMaxSize(),60L, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(), threadNameFactory);
    }

    @Override
    public void shutdown() throws Exception {
        ExecutorUtil.shutdownAndAwaitTermination(this.executorService, 60, TimeUnit.SECONDS);
    }
}
