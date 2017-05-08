package com.snowcattle.game.service.async.pool;

import com.snowcattle.game.service.async.AsyncCall;

import java.util.concurrent.Future;

/**
 * Created by jiangwenping on 17/4/19.
 * 异步线程池
 */
public interface AsyncThreadPool {
    public Future submit(AsyncCall asyncCall);
}
