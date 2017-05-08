package com.snowcattle.game.common.thread.join;

/**
 * Created by jwp on 2017/4/12.
 */
public class ThreadTester {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new ThreadTesterA());
        Thread t2 = new Thread(new ThreadTesterB());
        t1.start();
        t1.join(); // wait t1 to be finished
        t2.start();
        t2.join(); // in this program, this may be removed
    }
}
