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
import com.wolf.shoot.common.config.GameServerDiffConfig;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.loader.DefaultClassLoader;
import com.wolf.shoot.common.util.BeanUtil;
import com.wolf.shoot.common.uuid.ClientSessionIdGenerator;
import com.wolf.shoot.logic.net.NetMessageDispatchLogic;
import com.wolf.shoot.logic.net.NetMessageProcessLogic;
import com.wolf.shoot.manager.spring.LocalSpringServiceManager;
import com.wolf.shoot.service.net.message.facade.GameFacade;
import com.wolf.shoot.service.net.message.facade.IFacade;
import com.wolf.shoot.service.net.message.factory.ITcpMessageFactory;
import com.wolf.shoot.service.net.message.factory.TcpMessageFactory;
import com.wolf.shoot.service.net.message.registry.MessageRegistry;
import com.wolf.shoot.service.net.pipeline.DefaultTcpServerPipeLine;
import com.wolf.shoot.service.net.pipeline.DefaultUdpServerPipeLine;
import com.wolf.shoot.service.net.pipeline.IServerPipeLine;
import com.wolf.shoot.service.net.pipeline.factory.DefaultTcpServerPipelineFactory;
import com.wolf.shoot.service.net.pipeline.factory.DefaultUdpServerPipelineFactory;
import com.wolf.shoot.service.net.process.GameTcpMessageProcessor;
import com.wolf.shoot.service.net.process.GameUdpMessageProcessor;
import com.wolf.shoot.service.net.process.QueueMessageExecutorProcessor;
import com.wolf.shoot.service.net.process.QueueTcpMessageExecutorProcessor;
import com.wolf.shoot.service.net.session.builder.NettyTcpSessionBuilder;
import com.wolf.shoot.service.net.session.builder.NettyUdpSessionBuilder;
import com.wolf.shoot.service.time.SystemTimeService;
import com.wolf.shoot.service.time.TimeService;

import java.util.concurrent.TimeUnit;

/**
 * Created by jiangwenping on 17/2/7.
 * 各种全局的业务管理器、公共服务实例的持有者，负责各种管理器的初始化和实例的获取
 */
public class Globals {

    /**
     * 服务器启动时调用，初始化所有管理器实例
     * @param configFile
     * @throws Exception
     */
    public static void init(String configFile) throws Exception {
        initLocalManger();
        LocalMananger.getInstance().create(GameServerConfigService.class, GameServerConfigService.class);
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        GameServerDiffConfig gameServerDiffConfig = gameServerConfigService.getGameServerDiffConfig();

        //初始化构造器
        initBuilder();
        //初始化工厂
        initFactory();
        //初始化uuid
        initIdGenerator();
        //初始化本地服务
        initLocalService();

        //初始化消息处理器
        initNetMessageProcessor();
        //时间服务
        LocalMananger.getInstance().create(SystemTimeService.class, TimeService.class);

        //注册classloader
        LocalMananger.getInstance().create(DefaultClassLoader.class, DefaultClassLoader.class);

        //消息注册
        LocalMananger.getInstance().create(MessageRegistry.class, MessageRegistry.class);

        //注册协议处理
        LocalMananger.getInstance().create(GameFacade.class, IFacade.class);

        initLogic();

    }

    public static void initLocalManger() throws Exception{
        LocalSpringServiceManager localSpringServiceManager = (LocalSpringServiceManager) BeanUtil.getBean("localSpringServiceManager");
        LocalMananger.getInstance().setLocalSpringServiceManager(localSpringServiceManager);
        localSpringServiceManager.start();
    }

    public static  void initLogic() throws Exception{
        LocalMananger.getInstance().create(NetMessageDispatchLogic.class, NetMessageDispatchLogic.class);
        LocalMananger.getInstance().create(NetMessageProcessLogic.class, NetMessageProcessLogic.class);
    }


    public static void initLocalService() throws  Exception{
        //初始化lookupservice
        initLookUpService();

        //初始化game-excutor更新服务
        initUpdateService();
    }

    public static void initUpdateService() throws  Exception{
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        EventBus eventBus = new EventBus();
        EventBus updateEventBus = new EventBus();
        int corePoolSize = gameServerConfigService.getGameServerConfig().getGameExcutorCorePoolSize();
        long keepAliveTime = gameServerConfigService.getGameServerConfig().getGameExcutorKeepAliveTime();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        GameServerConfig gameServerConfig = gameServerConfigService.getGameServerConfig();
        int cycleSleepTime = gameServerConfigService.getGameServerConfig().getGameExcutorCycleTime() / Constants.cycle.cycleSize;
        long minCycleTime = gameServerConfigService.getGameServerConfig().getGameExcutorMinCycleTime() * cycleSleepTime;

        if(gameServerConfig.isUpdateServiceExcutorFlag()) {
            UpdateEventExcutorService updateEventExcutorService = new UpdateEventExcutorService(corePoolSize);

            LockSupportEventDisptachThread dispatchThread = new LockSupportEventDisptachThread(updateEventBus, updateEventExcutorService
                    , cycleSleepTime, minCycleTime);
            updateEventExcutorService.setDispatchThread(dispatchThread);
            UpdateService updateService = new UpdateService(dispatchThread, updateEventExcutorService);
            updateEventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
            updateEventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
            updateEventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));
            LocalMananger.getInstance().add(updateService, UpdateService.class);
        }else{
            UpdateExecutorService updateExecutorService = new UpdateExecutorService(corePoolSize, keepAliveTime, timeUnit);
            LockSupportDisptachThread dispatchThread = new LockSupportDisptachThread(updateEventBus, updateExecutorService
                    , cycleSleepTime, minCycleTime);
            UpdateService updateService = new UpdateService(dispatchThread, updateExecutorService);
            updateEventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
            updateEventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
            updateEventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));
        }
    }

    public static void initNetMessageProcessor() throws  Exception{
        int tcpWorkersize = 0;
        QueueTcpMessageExecutorProcessor queueTcpMessageExecutorProcessor  = new QueueTcpMessageExecutorProcessor(GlobalConstants.QueueMessageExecutor.processLeft, tcpWorkersize);
        GameTcpMessageProcessor gameTcpMessageProcessor = new GameTcpMessageProcessor(queueTcpMessageExecutorProcessor);
        LocalMananger.getInstance().add(gameTcpMessageProcessor, GameTcpMessageProcessor.class);

        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getLocalSpringServiceManager().getGameServerConfigService();
        int udpWorkerSize = gameServerConfigService.getGameServerConfig().getUpdQueueMessageProcessWorkerSize();
        QueueMessageExecutorProcessor queueMessageUdpExecutorProcessor = new QueueMessageExecutorProcessor(GlobalConstants.QueueMessageExecutor.processLeft, udpWorkerSize);
        GameUdpMessageProcessor gameUdpMessageProcessor = new GameUdpMessageProcessor(queueMessageUdpExecutorProcessor);
        LocalMananger.getInstance().add(gameUdpMessageProcessor, GameUdpMessageProcessor.class);

    }

    public static void initIdGenerator() throws Exception{
        LocalMananger.getInstance().create(ClientSessionIdGenerator.class, ClientSessionIdGenerator.class);
    }

    public static void initBuilder() throws Exception {
        //注册tcp session的构造器
        LocalMananger.getInstance().create(NettyTcpSessionBuilder.class, NettyTcpSessionBuilder.class);

        //注册udp session的构造器
        LocalMananger.getInstance().create(NettyUdpSessionBuilder.class, NettyUdpSessionBuilder.class);
    }

    public static void initLookUpService() throws Exception{

        //注册session查找
//        LocalMananger.getInstance().create(NetTcpSessionLoopUpService.class, NetTcpSessionLoopUpService.class);
//        LocalMananger.getInstance().create(GamePlayerLoopUpService.class, GamePlayerLoopUpService.class);
    }

    public static void initFactory() throws Exception {

        //注册管道工厂

        //注册tcp管道
        LocalMananger.getInstance().create(DefaultTcpServerPipelineFactory.class, DefaultTcpServerPipelineFactory.class);
        DefaultTcpServerPipelineFactory defaultTcpServerPipelineFactory = LocalMananger.getInstance().get(DefaultTcpServerPipelineFactory.class);
        IServerPipeLine defaultTcpServerPipeline = defaultTcpServerPipelineFactory.createServerPipeLine();
        LocalMananger.getInstance().add(defaultTcpServerPipeline, DefaultTcpServerPipeLine.class);

        //注册udp协议管道
        LocalMananger.getInstance().create(DefaultUdpServerPipelineFactory.class, DefaultUdpServerPipelineFactory.class);
        DefaultUdpServerPipelineFactory defaultUdpServerPipelineFactory = LocalMananger.getInstance().get(DefaultUdpServerPipelineFactory.class);
        IServerPipeLine defaultUdpServerPipline = defaultUdpServerPipelineFactory.createServerPipeLine();
        LocalMananger.getInstance().add(defaultUdpServerPipline, DefaultUdpServerPipeLine.class);

        //注册协议工厂
        LocalMananger.getInstance().create(TcpMessageFactory.class, ITcpMessageFactory.class);
    }

    public static void start() throws Exception{
        UpdateService updateService = LocalMananger.getInstance().get(UpdateService.class);
        updateService.start();

        GameUdpMessageProcessor gameUdpMessageProcessor = LocalMananger.getInstance().get(GameUdpMessageProcessor.class);
        gameUdpMessageProcessor.start();
    }

    public static void stop() throws Exception{
        UpdateService updateService = LocalMananger.getInstance().get(UpdateService.class);
        updateService.stop();

        GameUdpMessageProcessor gameUdpMessageProcessor = LocalMananger.getInstance().get(GameUdpMessageProcessor.class);
        gameUdpMessageProcessor.stop();
    }
}
