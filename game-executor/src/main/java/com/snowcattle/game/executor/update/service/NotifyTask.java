package com.snowcattle.game.executor.update.service;

import java.util.TimerTask;

/**
 * Created by jwp on 2017/3/28.
 * 任务通知
 */
public class NotifyTask extends TimerTask {

    private final UpdateService updateService;

    public NotifyTask(UpdateService updateService) {
        this.updateService = updateService;
    }

    @Override
    public void run() {
        updateService.notifyRun();
    }
}
