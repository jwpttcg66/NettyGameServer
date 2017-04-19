package com.wolf.shoot.service.async.pool;

import com.wolf.shoot.service.async.AsyncCall;

import java.util.concurrent.Future;

/**
 * Created by jiangwenping on 17/4/19.
 * 异步线程池
 */
public interface AsyncThreadPool {
    public Future submit(AsyncCall asyncCall);
}
