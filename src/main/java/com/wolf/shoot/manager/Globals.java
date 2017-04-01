package com.wolf.shoot.manager;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.impl.DispatchCreateEventListener;
import com.snowcattle.game.excutor.event.impl.DispatchFinishEventListener;
import com.snowcattle.game.excutor.event.impl.DispatchUpdateEventListener;
import com.snowcattle.game.excutor.pool.UpdateEventExcutorService;
import com.snowcattle.game.excutor.pool.UpdateExecutorService;
import com.snowcattle.game.excutor.service.UpdateService;
import com.snowcattle.game.excutor.thread.LockSupportDisptachThread;
import com.snowcattle.game.excutor.thread.LockSupportEventDisptachThread;
import com.snowcattle.game.excutor.utils.Constants;
import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.util.BeanUtil;
import com.wolf.shoot.manager.spring.LocalSpringBeanManager;
import com.wolf.shoot.manager.spring.LocalSpringServiceManager;
import com.wolf.shoot.manager.spring.LocalSpringServicerAfterManager;
import com.wolf.shoot.service.net.process.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/2/7.
 * 各种全局的业务管理器、公共服务实例的持有者，负责各种管理器的初始化和实例的获取
 */
public class Globals {

    /**
     * 服务器启动时调用，初始化所有管理器实例
     *
     * @param configFile
     * @throws Exception
     */
    public static void init(String configFile) throws Exception {
        initLocalManger();
        //初始化本地服务
        initLocalService();

        //初始化消息处理器
        initNetMessageProcessor();

    }

    public static void initLocalManger() throws Exception {

        LocalSpringBeanManager localSpringBeanManager = (LocalSpringBeanManager) BeanUtil.getBean("localSpringBeanManager");
        LocalMananger.getInstance().setLocalSpringBeanManager(localSpringBeanManager);
        LocalSpringServiceManager localSpringServiceManager = (LocalSpringServiceManager) BeanUtil.getBean("localSpringServiceManager");
        LocalMananger.getInstance().setLocalSpringServiceManager(localSpringServiceManager);
        localSpringServiceManager.start();

        LocalSpringServicerAfterManager localSpringServicerAfterManager = (LocalSpringServicerAfterManager) BeanUtil.getBean("localSpringServicerAfterManager");
        LocalMananger.getInstance().setLocalSpringServicerAfterManager(localSpringServicerAfterManager);
        localSpringServicerAfterManager.start();
    }


    public static void initLocalService() throws Exception {
        //初始化game-excutor更新服务
        initUpdateService();
    }

    public static void initUpdateService() throws Exception {
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
            UpdateEventExcutorService updateEventExcutorService = new UpdateEventExcutorService(corePoolSize);

            LockSupportEventDisptachThread dispatchThread = new LockSupportEventDisptachThread(updateEventBus, updateEventExcutorService
                    , cycleSleepTime, minCycleTime);
            updateEventExcutorService.setDispatchThread(dispatchThread);
            UpdateService updateService = new UpdateService(dispatchThread, updateEventExcutorService);
            updateEventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
            updateEventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
            updateEventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));
            LocalMananger.getInstance().add(updateService, UpdateService.class);
        } else {
            UpdateExecutorService updateExecutorService = new UpdateExecutorService(corePoolSize, keepAliveTime, timeUnit);
            LockSupportDisptachThread dispatchThread = new LockSupportDisptachThread(updateEventBus, updateExecutorService
                    , cycleSleepTime, minCycleTime);
            UpdateService updateService = new UpdateService(dispatchThread, updateExecutorService);
            updateEventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
            updateEventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
            updateEventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));
        }
    }

    public static void initNetMessageProcessor() throws Exception {
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


    public static void start() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        UpdateService updateService = LocalMananger.getInstance().get(UpdateService.class);
        updateService.start();

        if(gameServerConfigService.getGameServerConfig().isUdpMessageOrderQueueFlag()) {
            GameUdpMessageOrderProcessor gameUdpMessageOrderProcessor = LocalMananger.getInstance().get(GameUdpMessageOrderProcessor.class);
            gameUdpMessageOrderProcessor.start();
        }else{
            GameUdpMessageProcessor gameUdpMessageProcessor = LocalMananger.getInstance().get(GameUdpMessageProcessor.class);
            gameUdpMessageProcessor.start();
        }
    }

    public static void stop() throws Exception {
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        UpdateService updateService = LocalMananger.getInstance().get(UpdateService.class);
        updateService.stop();

        if(gameServerConfigService.getGameServerConfig().isUdpMessageOrderQueueFlag()) {
            GameUdpMessageOrderProcessor gameUdpMessageOrderProcessor = LocalMananger.getInstance().get(GameUdpMessageOrderProcessor.class);
            gameUdpMessageOrderProcessor.stop();
        }else {
            GameUdpMessageProcessor gameUdpMessageProcessor = LocalMananger.getInstance().get(GameUdpMessageProcessor.class);
            gameUdpMessageProcessor.stop();
        }

        LocalMananger.getInstance().getLocalSpringServiceManager().stop();
        LocalMananger.getInstance().getLocalSpringServicerAfterManager().stop();
    }

}
