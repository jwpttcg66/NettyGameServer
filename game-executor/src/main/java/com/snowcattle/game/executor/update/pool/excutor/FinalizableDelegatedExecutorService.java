package com.snowcattle.game.executor.update.pool.excutor;

import java.util.concurrent.ExecutorService;

/**
 * Created by jwp on 2017/2/23.
 */
class FinalizableDelegatedExecutorService extends DelegatedExecutorService {
    FinalizableDelegatedExecutorService(ExecutorService executor) {
        super(executor);
    }

    protected void finalize() {
        super.shutdown();
    }
}