package com.wolf.shoot.common.thread.join;

/**
 * Created by jwp on 2017/4/12.
 */
class ThreadTesterB implements Runnable {

    private int i;

    @Override
    public void run() {
        while (i <= 10) {
            System.out.print("i = " + i + " ");
            i++;
        }
        System.out.println();
    }
}
