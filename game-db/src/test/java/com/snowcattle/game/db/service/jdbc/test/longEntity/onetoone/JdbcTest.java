package com.snowcattle.game.db.service.jdbc.test.longEntity.onetoone;

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
//        insertTest(classPathXmlApplicationContext, orderService);
//        insertBatchTest(classPathXmlApplicationContext, orderService);
//        Order order = getTest(classPathXmlApplicationContext, orderService);
//        List<Order> orderList = getOrderList(classPathXmlApplicationContext, orderService);
        List<Order> orderList = filterList(classPathXmlApplicationContext, orderService);
//        updateTest(classPathXmlApplicationContext, orderService, order);
//        updateBatchTest(classPathXmlApplicationContext, orderService, orderList);
//        deleteTest(classPathXmlApplicationContext, orderService, order);
//        deleteBatchTest(classPathXmlApplicationContext, orderService, orderList);
//        getOrderList(classPathXmlApplicationContext, orderService);
    }



    public static void deleteBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, List<Order> orderList) throws Exception {
        orderService.deleteEntityBatch(orderList);
    }

    public static void updateBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, List<Order> orderList) throws Exception {
        EntityProxyFactory entityProxyFactory = (EntityProxyFactory) classPathXmlApplicationContext.getBean("entityProxyFactory");
        List<Order> updateList = new ArrayList<>();
        for (Order order : orderList) {
            Order proxyOrder = entityProxyFactory.createProxyEntity(order);
            proxyOrder.setStatus("dddd");
            proxyOrder.setUserId(TestConstants.userId);
            proxyOrder.setId(order.getId());
            updateList.add(proxyOrder);
        }
        orderService.updateOrderList(updateList);
    }

    public static void insertBatchTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) throws Exception {
        int startSize = TestConstants.batchStart;
        int endSize = startSize + TestConstants.saveSize;
        List<Order> list = new ArrayList<>();
        for (int i = startSize; i < endSize; i++) {
            Order order = new Order();
            order.setUserId(TestConstants.userId);
            order.setId((long)i);
            order.setStatus("测试列表插入" + i);
            list.add(order);
        }

        orderService.insertOrderList(list);
    }

    public static List<Order> getOrderList(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) throws Exception {
        List<Order> orderList = orderService.getOrderList(TestConstants.userId);
        System.out.println(orderList);
        return orderList;
    }

    public static List<Order> filterList(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) throws Exception {
        EntityProxyFactory entityProxyFactory = (EntityProxyFactory) classPathXmlApplicationContext.getBean("entityProxyFactory");
        Order order = new Order();
        Order proxyOrder = entityProxyFactory.createProxyEntity(order);
        proxyOrder.setUserId(TestConstants.userId);
        proxyOrder.setStatus("1");
        List<Order> orderList = orderService.filterList(proxyOrder);
        System.out.println(orderList);
        return orderList;
    }

    public static void insertTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) {

        int startSize = TestConstants.batchStart;
        int endSize = TestConstants.batchStart+1;

        for (int i = startSize; i < endSize; i++) {
            Order order = new Order();
            order.setUserId(TestConstants.userId);
            order.setId((long) i);
            order.setStatus("测试插入" + i);
            orderService.insertOrder(order);
        }
    }

    public static Order getTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService) {
        Order order = orderService.getOrder(TestConstants.userId, TestConstants.id);
        System.out.println(order);
        return order;
    }


    public static void updateTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, Order order) throws Exception {
        EntityProxyFactory entityProxyFactory = (EntityProxyFactory) classPathXmlApplicationContext.getBean("entityProxyFactory");
        Order proxyOrder = entityProxyFactory.createProxyEntity(order);
        proxyOrder.setStatus("修改了3");
        orderService.updateOrder(proxyOrder);

        Order queryOrder = orderService.getOrder(TestConstants.userId, TestConstants.id);
        System.out.println(queryOrder.getStatus());
    }

    public static void deleteTest(ClassPathXmlApplicationContext classPathXmlApplicationContext, OrderService orderService, Order order) throws Exception {
        orderService.deleteOrder(order);
        Order queryOrder = orderService.getOrder(TestConstants.userId, TestConstants.id);
        System.out.println(queryOrder);
    }

    public static OrderService getOrderService(ClassPathXmlApplicationContext classPathXmlApplicationContext) {
        OrderService orderService = (OrderService) classPathXmlApplicationContext.getBean("orderService");
        return orderService;
    }
}
