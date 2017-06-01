package com.snowcattle.game.common.thread.join;

/**
 * Created by jwp on 2017/4/12.
 */
class ThreadTesterA implements Runnable {

    private int counter;

    @Override
    public void run() {
        while (counter <= 1000) {
            System.out.println("Counter = " + counter + " ");
            counter++;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }
}