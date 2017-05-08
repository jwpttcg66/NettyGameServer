package com.snowcattle.game.manager;

import com.snowcattle.game.common.config.GameServerConfig;
import com.snowcattle.game.common.config.GameServerConfigService;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.executor.event.EventBus;
import com.snowcattle.game.executor.event.impl.listener.DispatchCreateEventListener;
import com.snowcattle.game.executor.event.impl.listener.DispatchFinishEventListener;
import com.snowcattle.game.executor.event.impl.listener.DispatchUpdateEventListener;
import com.snowcattle.game.executor.event.service.AsyncEventService;
import com.snowcattle.game.executor.update.pool.UpdateBindExecutorService;
import com.snowcattle.game.executor.update.pool.UpdateExecutorService;
import com.snowcattle.game.executor.update.service.UpdateService;
import com.snowcattle.game.executor.update.thread.dispatch.BindDisptachThread;
import com.snowcattle.game.executor.update.thread.dispatch.LockSupportDisptachThread;
import com.snowcattle.game.manager.spring.LocalSpringBeanManager;
import com.snowcattle.game.manager.spring.LocalSpringServiceManager;
import com.snowcattle.game.manager.spring.LocalSpringServicerAfterManager;
import com.snowcattle.game.service.net.process.*;
import com.snowcattle.game.common.util.BeanUtil;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/2/7.
 * 各种全局的业务管理器、公共服务实例的持有者，负责各种管理器的初始化和实例的获取
 */
public class GlobalManager {

    /**
     * 服务器启动时调用，初始化所有管理器实例
     *
     * @param configFile
     * @throws Exception
     */
    public void init(String configFile) throws Exception {
        initLocalManger();
        //初始化本地服务
        initLocalService();

        //初始化消息处理器
        initNetMessageProcessor();

        initGameManager();

    }

    //拓展使用
    public void initGameManager() throws Exception {

    }

    public void initLocalManger() throws Exception {

        LocalSpringBeanManager localSpringBeanManager = (LocalSpringBeanManager) BeanUtil.getBean("localSpringBeanManager");
        LocalMananger.getInstance().setLocalSpringBeanManager(localSpringBeanManager);
        LocalSpringServiceManager localSpringServiceManager = (LocalSpringServiceManager) BeanUtil.getBean("localSpringServiceManager");
        LocalMananger.getInstance().setLocalSpringServiceManager(localSpringServiceManager);
        localSpringServiceManager.start();

        LocalSpringServicerAfterManager localSpringServicerAfterManager = (LocalSpringServicerAfterManager) BeanUtil.getBean("localSpringServicerAfterManager");
        LocalMananger.getInstance().setLocalSpringServicerAfterManager(localSpringServicerAfterManager);
        localSpringServicerAfterManager.start();
    }


    public void initLocalService() throws Exception {
        //初始化game-excutor更新服务
        initUpdateService();
        //初始化事件服务
        initEventService();
    }

    public void initEventService() throws Exception{
        EventBus eventBus = new EventBus();
        AsyncEventService asyncEventService = new AsyncEventService(eventBus, Short.MAX_VALUE * 10, 1, GlobalConstants.Thread.EVENT_WORKER, 60, GlobalConstants.Thread.EVENT_HANDLER, Integer.MAX_VALUE);
        LocalMananger.getInstance().add(asyncEventService, AsyncEventService.class);
    }

    public void initUpdateService() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        EventBus eventBus = new EventBus();
        EventBus updateEventBus = new EventBus();
        int corePoolSize = gameServerConfigService.getGameServerConfig().getGameExcutorCorePoolSize();
        long keepAliveTime = gameServerConfigService.getGameServerConfig().getGameExcutorKeepAliveTime();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        int cycleSleepTime = gameServerConfigService.getGameServerConfig().getGameExcutorCycleTime() / Constants.cycle.cycleSize;
        long minCycleTime = gameServerConfigService.getGameServerConfig().getGameExcutorMinCycleTime() * cycleSleepTime;

        if (gameServerConfig.isUpdateServiceExcutorFlag()) {
            UpdateBindExecutorService updateBindExcutorService = new UpdateBindExecutorService(corePoolSize);

            BindDisptachThread dispatchThread = new BindDisptachThread(updateEventBus, updateBindExcutorService
                    , cycleSleepTime, cycleSleepTime*1000);
            updateBindExcutorService.setDispatchThread(dispatchThread);
            UpdateService updateService = new UpdateService(dispatchThread, updateBindExcutorService);
            updateEventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
            updateEventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
            updateEventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));
            LocalMananger.getInstance().add(updateService, UpdateService.class);

        } else {
            UpdateExecutorService updateExecutorService = new UpdateExecutorService(corePoolSize);
            LockSupportDisptachThread dispatchThread = new LockSupportDisptachThread(updateEventBus, updateExecutorService
                    , cycleSleepTime, minCycleTime);
            UpdateService updateService = new UpdateService(dispatchThread, updateExecutorService);
            updateEventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
            updateEventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
            updateEventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));
            LocalMananger.getInstance().add(updateService, UpdateService.class);

        }
    }

    public void initNetMessageProcessor() throws Exception {
        //tcp处理队列
        int tcpWorkersize = 0;
        QueueTcpMessageExecutorProcessor queueTcpMessageExecutorProcessor = new QueueTcpMessageExecutorProcessor(GlobalConstants.QueueMessageExecutor.processLeft, tcpWorkersize);
        GameTcpMessageProcessor gameTcpMessageProcessor = new GameTcpMessageProcessor(queueTcpMessageExecutorProcessor);
        LocalMananger.getInstance().add(gameTcpMessageProcessor, GameTcpMessageProcessor.class);

        //udp处理队列
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        int udpWorkerSize = gameServerConfigService.getGameServerConfig().getUpdQueueMessageProcessWorkerSize();
        if (gameServerConfigService.getGameServerConfig().isUdpMessageOrderQueueFlag()) {
            //OrderedQueuePoolExecutor 顺序模型
            GameUdpMessageOrderProcessor gameUdpMessageOrderProcessor = new GameUdpMessageOrderProcessor();
            LocalMananger.getInstance().add(gameUdpMessageOrderProcessor, GameUdpMessageOrderProcessor.class);
        } else {
            //生产者消费者模型
            QueueMessageExecutorProcessor queueMessageUdpExecutorProcessor = new QueueMessageExecutorProcessor(GlobalConstants.QueueMessageExecutor.processLeft, udpWorkerSize);
            GameUdpMessageProcessor gameUdpMessageProcessor = new GameUdpMessageProcessor(queueMessageUdpExecutorProcessor);
            LocalMananger.getInstance().add(gameUdpMessageProcessor, GameUdpMessageProcessor.class);
        }
    }


    /**
     * 非spring的start
     * @throws Exception
    */
    public void start() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        UpdateService updateService = LocalMananger.getInstance().get(UpdateService.class);
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        if (gameServerConfig.isUpdateServiceExcutorFlag()) {
            updateService.notifyStart();
        }else {
            updateService.start();
        }

        AsyncEventService asyncEventService = LocalMananger.getInstance().getAsyncEventService();
        asyncEventService.startUp();

        if(gameServerConfigService.getGameServerConfig().isUdpMessageOrderQueueFlag()) {
            GameUdpMessageOrderProcessor gameUdpMessageOrderProcessor = LocalMananger.getInstance().get(GameUdpMessageOrderProcessor.class);
            gameUdpMessageOrderProcessor.start();
        }else{
            GameUdpMessageProcessor gameUdpMessageProcessor = LocalMananger.getInstance().get(GameUdpMessageProcessor.class);
            gameUdpMessageProcessor.start();
        }

        startGameManager();
    }

    public void startGameManager() throws Exception{

    }

    public void stop() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        UpdateService updateService = LocalMananger.getInstance().get(UpdateService.class);
        updateService.stop();

        AsyncEventService asyncEventService = LocalMananger.getInstance().getAsyncEventService();
        asyncEventService.shutDown();

        if(gameServerConfigService.getGameServerConfig().isUdpMessageOrderQueueFlag()) {
            GameUdpMessageOrderProcessor gameUdpMessageOrderProcessor = LocalMananger.getInstance().get(GameUdpMessageOrderProcessor.class);
            gameUdpMessageOrderProcessor.stop();
        }else {
            GameUdpMessageProcessor gameUdpMessageProcessor = LocalMananger.getInstance().get(GameUdpMessageProcessor.class);
            gameUdpMessageProcessor.stop();
        }

        LocalMananger.getInstance().getLocalSpringServiceManager().stop();
        LocalMananger.getInstance().getLocalSpringServicerAfterManager().stop();

        stopGameManager();
    }

    public void stopGameManager() throws Exception{

    }
}
