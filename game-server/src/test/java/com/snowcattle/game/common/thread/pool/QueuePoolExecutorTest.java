package com.snowcattle.game.common.thread.pool;


import com.snowcattle.game.thread.executor.NonOrderedQueuePoolExecutor;
import com.snowcattle.game.thread.executor.OrderedQueuePoolExecutor;

/**
 * Created by jiangwenping on 17/3/10.
 */
public class QueuePoolExecutorTest {

    public static void main(String[] args) {
        testOrder();
//        testNoOrder();
    }

    public static void testOrder(){
        OrderedQueuePoolExecutor orderedQueuePoolExecutor = new OrderedQueuePoolExecutor("orderpool", 5, Integer.MAX_VALUE);
        int maxSize = 50;
        for(int i = 0 ; i < maxSize; i++) {
            orderedQueuePoolExecutor.addTask(1, new TestWorker(i));
        }
    }

    public static void testNoOrder(){
        NonOrderedQueuePoolExecutor nonOrderedQueuePoolExecutor = new NonOrderedQueuePoolExecutor(5);
        int maxSize = 50;
        for(int i = 0 ; i < maxSize; i++) {
            nonOrderedQueuePoolExecutor.executeWork(new TestWorker(i));
        }
    }
}
