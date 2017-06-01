package com.snowcattle.game.db.service.jdbc.test.stringEntity.onetoone;

import com.snowcattle.game.db.service.async.AsyncDbOperationCenter;
import com.snowcattle.game.db.service.jdbc.entity.Tocken;
import com.snowcattle.game.db.service.jdbc.service.entity.impl.TockenService;
import com.snowcattle.game.db.service.proxy.EntityAysncServiceProxyFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by jiangwenping on 17/3/29.
 */
public class JdbcAsyncCacheTest extends JdbcCacheTest{

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});

        TockenService tockenService = getOrderProxyService(classPathXmlApplicationContext);
        insertTest(classPathXmlApplicationContext, tockenService);
        insertBatchTest(classPathXmlApplicationContext, tockenService);
        Tocken tocken = getTest(classPathXmlApplicationContext, tockenService);
        List<Tocken> orderList = getTockenList(classPathXmlApplicationContext, tockenService);
        updateTest(classPathXmlApplicationContext, tockenService, tocken);
        updateBatchTest(classPathXmlApplicationContext, tockenService, orderList);
        deleteTest(classPathXmlApplicationContext, tockenService, tocken);
        deleteBatchTest(classPathXmlApplicationContext, tockenService, orderList);

        AsyncDbOperationCenter asyncDbOperationCenter = (AsyncDbOperationCenter) classPathXmlApplicationContext.getBean("asyncDbOperationCenter");
        asyncDbOperationCenter.startup();

    }

    public static TockenService getOrderProxyService(ClassPathXmlApplicationContext classPathXmlApplicationContext) throws Exception {
        TockenService tockenService = (TockenService) classPathXmlApplicationContext.getBean("tockenService");
        EntityAysncServiceProxyFactory entityAysncServiceProxyFactory = (EntityAysncServiceProxyFactory) classPathXmlApplicationContext.getBean("entityAysncServiceProxyFactory");
        tockenService = entityAysncServiceProxyFactory.createProxyService(tockenService);
        return tockenService;
    }
}
