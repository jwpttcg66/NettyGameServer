package com.wolf.shoot.server;

import com.wolf.shoot.common.ThreadNameFactory;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.common.constant.ServiceName;
import com.wolf.shoot.common.util.ThreadPool;
import com.wolf.shoot.manager.LocalMananger;
import com.wolf.shoot.service.IService;
import com.wolf.shoot.service.net.GameNettyRPCService;
import com.wolf.shoot.service.net.GameNettyTcpServerService;
import com.wolf.shoot.service.net.GameNettyUdpServerService;
import com.wolf.shoot.service.net.LocalNetService;
import org.slf4j.Logger;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * Created by jiangwenping on 17/3/13.
 * 服务器启动结束服务
 */
public class GamerServerStartFinishedService implements IService{

    private Logger logger = Loggers.serverLogger;
    private LocalNetService localNetService;

    private ThreadPool threadPool;

    public LocalNetService getLocalNetService() {
        return localNetService;
    }

    public void setLocalNetService(LocalNetService localNetService) {
        this.localNetService = localNetService;
    }

    @Override
    public String getId() {
        return ServiceName.GamerServerStartFinishedService;
    }

    @Override
    public void startup() throws Exception {
        int coreSize = 3;
        ThreadNameFactory threadNameFactory = new ThreadNameFactory(GlobalConstants.Thread.START_FINISHED);
        localNetService = LocalMananger.getInstance().get(LocalNetService.class);
        threadPool = new ThreadPool(coreSize, coreSize, 0, new ArrayBlockingQueue<Runnable>(coreSize),threadNameFactory, new ThreadPoolExecutor.DiscardOldestPolicy());
        threadPool.start();
        threadPool.addTask(new Runnable() {
            @Override
            public void run() {
                GameNettyTcpServerService gameNettyTcpServerService = localNetService.getGameNettyTcpServerService();
                if(gameNettyTcpServerService != null){
                    try {
                        gameNettyTcpServerService.finish();
                    } catch (Exception e) {
                        Loggers.errorLogger.error(e.toString(), e);
                    }
                }
            }
        });

        threadPool.addTask(new Runnable() {
            @Override
            public void run() {
                GameNettyUdpServerService gameNettyUdpServerService = localNetService.getGameNettyUdpServerService();
                if(gameNettyUdpServerService != null){
                    try {
                        gameNettyUdpServerService.finish();
                    } catch (Exception e) {
                        Loggers.errorLogger.error(e.toString(), e);
                    }
                }
            }
        });

        threadPool.addTask(new Runnable() {
            @Override
            public void run() {
                GameNettyRPCService gameNettyRPCService = localNetService.getGameNettyRPCService();
                if(gameNettyRPCService != null){
                    try {
                        gameNettyRPCService.finish();
                    } catch (Exception e) {
                        Loggers.errorLogger.error(e.toString(), e);
                    }
                }
            }
        });

        logger.info("netty server sync start finished");

    }

    @Override
    public void shutdown() throws Exception {
       threadPool.stop();
    }
}
