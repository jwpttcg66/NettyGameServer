package com.snowcattle.game.common.thread.pool;


import com.snowcattle.game.thread.worker.AbstractWork;

/**
 * Created by jiangwenping on 17/3/10.
 */
public class TestWorker extends AbstractWork {

    private int i;

    public TestWorker(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println(i);
    }
}
