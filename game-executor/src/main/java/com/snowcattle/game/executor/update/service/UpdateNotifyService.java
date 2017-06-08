package com.snowcattle.game.executor.update.service;

import com.snowcattle.game.common.utils.ExecutorUtil;
import com.snowcattle.game.executor.common.utils.Constants;
import com.snowcattle.game.thread.ThreadNameFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by jwp on 2017/5/5.
 * updateservice的唤醒服务
 */
public class UpdateNotifyService {

    private ScheduledExecutorService scheduledExecutorService;


    private UpdateService updateService;

    /**
     * 单位毫秒
     */
    private int notifyTime;

    public UpdateNotifyService(UpdateService updateService, int notifyTime) {
        this.updateService = updateService;
        this.notifyTime = notifyTime;
    }

    public void startup() throws Exception {
        ThreadNameFactory threadNameFactory = new ThreadNameFactory(Constants.Thread.UpdateNotifyService);
        scheduledExecutorService = Executors.newScheduledThreadPool(1, threadNameFactory);
        scheduledExecutorService.scheduleAtFixedRate(new NotifyTask(updateService), notifyTime, 1, TimeUnit.MICROSECONDS);
    }

    public void shutdown() throws Exception {
        ExecutorUtil.shutdownAndAwaitTermination(scheduledExecutorService, 60L, TimeUnit.MILLISECONDS);
    }
}
