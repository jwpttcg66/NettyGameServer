package com.wolf.shoot.server;

import com.wolf.shoot.common.ThreadNameFactory;
import com.wolf.shoot.common.constant.GlobalConstants;
import com.wolf.shoot.common.util.ThreadPool;
import com.wolf.shoot.service.net.GameNettyTcpServerService;
import com.wolf.shoot.service.net.LocalNetService;

import java.util.concurrent.*;
/**
 * Created by jiangwenping on 17/3/13.
 * 服务器启动结束服务
 */
public class GamerServerStartFinishedService {

    private LocalNetService localNetService;

    private ThreadPool threadPool;
    public void finish(){
        int coreSize = 3;
        ThreadNameFactory threadNameFactory = new ThreadNameFactory(GlobalConstants.Thread.START_FINISHED);
        threadPool = new ThreadPool(coreSize, coreSize, 0, new ArrayBlockingQueue<Runnable>(coreSize),threadNameFactory, new ThreadPoolExecutor.DiscardOldestPolicy());
        threadPool.addTask(new Runnable() {
            @Override
            public void run() {
                GameNettyTcpServerService gameNettyTcpServerService = localNetService.getGameNettyTcpServerService();
                if(gameNettyTcpServerService != null){
                    try {
                        gameNettyTcpServerService.finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
