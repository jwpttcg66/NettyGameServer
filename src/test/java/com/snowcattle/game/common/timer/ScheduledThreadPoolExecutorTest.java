package com.snowcattle.game.common.timer;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by jwp on 2017/3/28.
 */

public class ScheduledThreadPoolExecutorTest
{
    private static long start;

    public static void main(String[] args)
    {
        /**
         * 使用工厂方法初始化一个ScheduledThreadPool
         */
        ScheduledExecutorService newScheduledThreadPool = Executors
                .newScheduledThreadPool(2);

        TimerTask task1 = new TimerTask()
        {
            @Override
            public void run()
            {
                try
                {

                    System.out.println("task1 invoked ! "
                            + (System.currentTimeMillis() - start));
                    Thread.sleep(3000);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        };

        TimerTask task2 = new TimerTask()
        {
            @Override
            public void run()
            {
                System.out.println("task2 invoked ! "
                        + (System.currentTimeMillis() - start));
            }
        };
        start = System.currentTimeMillis();
        newScheduledThreadPool.schedule(task1, 1000, TimeUnit.MILLISECONDS);
//        newScheduledThreadPool.scheduleAtFixedRate(task1, 1000, 1000, TimeUnit.MICROSECONDS);
        newScheduledThreadPool.schedule(task2, 3000, TimeUnit.MILLISECONDS);
    }
}