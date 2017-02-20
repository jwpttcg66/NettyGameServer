package com.wolf.shoot.manager;

import com.snowcattle.game.excutor.event.EventBus;
import com.snowcattle.game.excutor.event.impl.*;
import com.snowcattle.game.excutor.pool.UpdateExecutorService;
import com.snowcattle.game.excutor.service.UpdateService;
import com.snowcattle.game.excutor.thread.LockSupportDisptachThread;
import com.snowcattle.game.excutor.utils.Constants;
import com.wolf.shoot.common.config.GameServerConfig;
import com.wolf.shoot.common.config.GameServerConfigService;
import com.wolf.shoot.common.config.GameServerDiffConfig;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.loader.DefaultClassLoader;
import com.wolf.shoot.common.uuid.ClientSessionIdGenerator;
import com.wolf.shoot.logic.net.NetMessageDispatchLogic;
import com.wolf.shoot.service.net.message.facade.GameFacade;
import com.wolf.shoot.service.net.message.facade.IFacade;
import com.wolf.shoot.service.net.message.factory.IMessageFactory;
import com.wolf.shoot.service.net.message.factory.MessageFactory;
import com.wolf.shoot.service.net.message.registry.MessageRegistry;
import com.wolf.shoot.service.net.session.builder.NettyTcpSessionBuilder;
import com.wolf.shoot.service.net.session.builder.NettyUdpSessionBuilder;
import com.wolf.shoot.service.lookup.NetTcpSessionLoopUpService;
import com.wolf.shoot.service.net.pipeline.DefaultTcpServerPipeline;
import com.wolf.shoot.service.net.pipeline.IServerPipeline;
import com.wolf.shoot.service.net.pipeline.factory.DefaultTcpServerPipelineFactory;
import com.wolf.shoot.service.net.process.GameMessageProcessor;
import com.wolf.shoot.service.net.process.IMessageProcessor;
import com.wolf.shoot.service.net.process.QueueMessageExecutorProcessor;
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

        LocalMananger.getInstance().create(GameServerConfigService.class, GameServerConfigService.class);
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getGameServerConfigService();
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

    public static  void initLogic() throws Exception{
        LocalMananger.getInstance().create(NetMessageDispatchLogic.class, NetMessageDispatchLogic.class);
    }


    public static void initLocalService() throws  Exception{
        //初始化lookupservice
        initLookUpService();

        //初始化game-excutor更新服务
        initUpdateService();
    }

    public static void initUpdateService() throws  Exception{
        GameServerConfigService gameServerConfigService = LocalMananger.getInstance().getGameServerConfigService();
        EventBus eventBus = new EventBus();
        EventBus updateEventBus = new EventBus();
        int corePoolSize = gameServerConfigService.getGameServerConfig().getGameExcutorCorePoolSize();
        long keepAliveTime = gameServerConfigService.getGameServerConfig().getGameExcutorKeepAliveTime();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        UpdateExecutorService updateExecutorService = new UpdateExecutorService(corePoolSize, keepAliveTime, timeUnit);
        int cycleTime = gameServerConfigService.getGameServerConfig().getGameExcutorCycleTime() / Constants.cycle.cycleSize;
        long minCycleTime = gameServerConfigService.getGameServerConfig().getGameExcutorMinCycleTime() * cycleTime;
        LockSupportDisptachThread dispatchThread = new LockSupportDisptachThread(eventBus, updateEventBus, updateExecutorService
                , cycleTime, minCycleTime);
        UpdateService updateService = new UpdateService(dispatchThread, updateEventBus, updateExecutorService);
        eventBus.addEventListener(new DispatchCreateEventListener(dispatchThread, updateService));
        eventBus.addEventListener(new DispatchUpdateEventListener(dispatchThread, updateService));
        eventBus.addEventListener(new DispatchFinishEventListener(dispatchThread, updateService));

        updateEventBus.addEventListener(new ReadyCreateEventListener());
        updateEventBus.addEventListener(new ReadyFinishEventListener());

        LocalMananger.getInstance().add(updateService, UpdateService.class);
    }

    public static void initNetMessageProcessor() throws  Exception{
        int size = 0;
        QueueMessageExecutorProcessor queueMessageExecutorProcessor  = new QueueMessageExecutorProcessor(GlobalConstants.QueueMessageExecutor.processLeft, size);
        GameMessageProcessor gameMessageProcessor = new GameMessageProcessor(queueMessageExecutorProcessor);
        LocalMananger.getInstance().add(gameMessageProcessor, IMessageProcessor.class);
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
        LocalMananger.getInstance().create(NetTcpSessionLoopUpService.class, NetTcpSessionLoopUpService.class);
    }

    public static void initFactory() throws Exception {

        //注册管道工厂
        LocalMananger.getInstance().create(DefaultTcpServerPipelineFactory.class, DefaultTcpServerPipelineFactory.class);
        DefaultTcpServerPipelineFactory defaultTcpServerPipelineFactory = LocalMananger.getInstance().get(DefaultTcpServerPipelineFactory.class);
        IServerPipeline defaultTcpServerPipeline = defaultTcpServerPipelineFactory.createServerPipeLine();
        LocalMananger.getInstance().add(defaultTcpServerPipeline, DefaultTcpServerPipeline.class);

        //注册协议工厂
        LocalMananger.getInstance().create(MessageFactory.class, IMessageFactory.class);
    }

    public static void start() throws Exception{
        UpdateService updateService = LocalMananger.getInstance().get(UpdateService.class);
        updateService.start();
    }

    public static void stop() throws Exception{
        UpdateService updateService = LocalMananger.getInstance().get(UpdateService.class);
        updateService.shutDown();
    }
}
