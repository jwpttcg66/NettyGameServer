package com.snowcattle.game.common.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jiangwenping on 17/3/13.
 */
public class ConditionTest {

    public ReentrantLock reentrantLock;
    public Condition condition;
    public static void main(String[] args) throws InterruptedException {
        ConditionTest conditionTest = new ConditionTest();
        conditionTest.reentrantLock = new ReentrantLock();
        conditionTest.condition = conditionTest.reentrantLock.newCondition();
        conditionTest.reentrantLock.lock();
        conditionTest.condition.signalAll();
        conditionTest.condition.await(6000, TimeUnit.MILLISECONDS);
        conditionTest.reentrantLock.unlock();
        System.out.println("done");
    }
}
