package com.snowcattle.game.common.thread.join;

/**
 * Created by jwp on 2017/4/12.
 */
class ThreadTesterB implements Runnable {

    private int i;

    @Override
    public void run() {
        while (i <= 1000) {
            System.out.println("i = " + i + " ");
            i++;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
    }
}
