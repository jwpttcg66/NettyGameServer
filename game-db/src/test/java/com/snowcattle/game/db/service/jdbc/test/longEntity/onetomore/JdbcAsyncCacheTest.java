package com.snowcattle.game.db.service.jdbc.test.longEntity.onetomore;

import com.snowcattle.game.db.service.async.AsyncDbOperationCenter;
import com.snowcattle.game.db.service.jdbc.entity.MoreOrder;
import com.snowcattle.game.db.service.jdbc.service.entity.impl.MoreOrderService;
import com.snowcattle.game.db.service.proxy.EntityServiceProxyFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by jiangwenping on 17/3/29.
 */
public class JdbcAsyncCacheTest extends JdbcCacheTest{

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});

        MoreOrderService orderService = getMoreOrderProxyService(classPathXmlApplicationContext);

        insertTest(classPathXmlApplicationContext, orderService);
        insertBatchTest(classPathXmlApplicationContext, orderService);
        MoreOrder order = getTest(classPathXmlApplicationContext, orderService);
        List<MoreOrder> orderList = getOrderList(classPathXmlApplicationContext, orderService);
        updateTest(classPathXmlApplicationContext, orderService, order);
        updateBatchTest(classPathXmlApplicationContext, orderService, orderList);
        deleteTest(classPathXmlApplicationContext, orderService, order);
        deleteBatchTest(classPathXmlApplicationContext, orderService, orderList);

        AsyncDbOperationCenter asyncDbOperationCenter = (AsyncDbOperationCenter) classPathXmlApplicationContext.getBean("asyncDbOperationCenter");
        asyncDbOperationCenter.startup();

    }

    public static MoreOrderService getMoreOrderProxyService(ClassPathXmlApplicationContext classPathXmlApplicationContext) throws Exception {
        MoreOrderService moreOrderService = (MoreOrderService) classPathXmlApplicationContext.getBean("moreOrderService");
        EntityServiceProxyFactory entityServiceProxyFactory = (EntityServiceProxyFactory) classPathXmlApplicationContext.getBean("entityServiceProxyFactory");
        moreOrderService = entityServiceProxyFactory.createProxyService(moreOrderService);
        return moreOrderService;
    }

}
