package com.wolf.shoot.common.thread.join;

/**
 * Created by jwp on 2017/4/12.
 */
class ThreadTesterA implements Runnable {

    private int counter;

    @Override
    public void run() {
        while (counter <= 10) {
            System.out.print("Counter = " + counter + " ");
            counter++;
        }
        System.out.println();
    }
}