package com.snowcattle.game.db.service.jdbc.test.tps.singleThread;

import com.snowcattle.game.db.service.common.uuid.SnowFlakeUUIDService;
import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.service.entity.impl.OrderService;
import com.snowcattle.game.db.service.jdbc.test.TestConstants;
import com.snowcattle.game.db.service.proxy.EntityProxyFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangwenping on 17/3/20.
 */
public class JdbcTest {

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        OrderService orderService = getOrderService(classPathXmlApplicationContext);
        SnowFlakeUUIDService snowFlakeUUIDService = (SnowFlakeUUIDService) classPathXmlApplicationContext.getBean("snowFlakeUUIDService");
        snowFlakeUUIDService.setNodeId(1);
        insertTest(classPathXmlApplicationContext, orderService, snowFlakeUUIDService);
    }

    public static void insertTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, SnowFlakeUUIDService snowFlakeUUIDService) {

        int startSize = TestConstants.batchStart;
        int endSize = TestConstants.batchStart+TestConstants.saveSize;

        long start = System.currentTimeMillis();
        for (int i = startSize; i < endSize; i++) {
            Order order = new Order();
            order.setUserId(TestConstants.userId);
            order.setId(snowFlakeUUIDService.nextId());
            order.setStatus("测试插入" + i);
            orderService.insertOrder(order);
        }
        long end  = System.currentTimeMillis();

        long time = end - start;
        System.out.println("存储" + TestConstants.saveSize + "耗时" + time);

    }

    public static OrderService getOrderService(ClassPathXmlApplicationContext classPathXmlApplicationContext) {
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        return orderService;
    }
}
