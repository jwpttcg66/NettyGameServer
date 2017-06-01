package com.snowcattle.game.db.service.jdbc.test.tps.mutilthread.sync;

import com.snowcattle.game.db.service.common.uuid.SnowFlakeUUIDService;
import com.snowcattle.game.db.service.jdbc.service.entity.impl.OrderService;
import com.snowcattle.game.db.service.jdbc.test.tps.mutilthread.SaveRunable;
import com.snowcattle.game.db.service.proxy.EntityServiceProxyFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by jwp on 2017/4/18.
 */
public class CacheTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        OrderService orderService = getOrderService(classPathXmlApplicationContext);
        SnowFlakeUUIDService snowFlakeUUIDService = (SnowFlakeUUIDService) classPathXmlApplicationContext.getBean("snowFlakeUUIDService");
        snowFlakeUUIDService.setNodeId(1);
        int threadSize = 3;
        CountDownLatch countDownLatch = new CountDownLatch(threadSize);
        AtomicLong number = new AtomicLong();

        long startTime = System.currentTimeMillis();
        for(int i = 0; i < threadSize; i++){
            SaveRunable saveRunable = new SaveRunable(orderService, snowFlakeUUIDService, number, countDownLatch);
            saveRunable.start();
        }
        countDownLatch.await();
        long endTime  = System.currentTimeMillis();

        long time = endTime - startTime;
        System.out.println("所有存储" + number.get() + "耗时" + time);
    }

    public static OrderService getOrderService(ClassPathXmlApplicationContext classPathXmlApplicationContext) throws Exception {
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        EntityServiceProxyFactory entityServiceProxyFactory = (EntityServiceProxyFactory) classPathXmlApplicationContext.getBean("entityServiceProxyFactory");
        orderService = entityServiceProxyFactory.createProxyService(orderService);
        return orderService;
    }
}
