package com.snowcattle.game.db.service.jdbc.test.stringEntity.onetoone;

import com.snowcattle.game.db.service.jdbc.entity.Tocken;
import com.snowcattle.game.db.service.jdbc.service.entity.impl.TockenService;
import com.snowcattle.game.db.service.jdbc.test.TestConstants;
import com.snowcattle.game.db.service.proxy.EntityProxyFactory;
import com.snowcattle.game.db.service.proxy.EntityServiceProxyFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by jiangwenping on 17/3/20.
 */
public class JdbcCacheTest extends JdbcTest{

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        TockenService tockenService = getTockenProxyService(classPathXmlApplicationContext);
        insertTest(classPathXmlApplicationContext, tockenService);
        insertBatchTest(classPathXmlApplicationContext, tockenService);
        Tocken tocken = getTest(classPathXmlApplicationContext, tockenService);
        List<Tocken> tockenList = getTockenList(classPathXmlApplicationContext, tockenService);
        updateTest(classPathXmlApplicationContext, tockenService, tocken);
        updateBatchTest(classPathXmlApplicationContext, tockenService, tockenList);
        deleteTest(classPathXmlApplicationContext, tockenService, tocken);
        deleteBatchTest(classPathXmlApplicationContext, tockenService, tockenList);
        getBatchTockenList(classPathXmlApplicationContext, tockenService);

    }

    public static List<Tocken> getBatchTockenList(ClassPathXmlApplicationContext classPathXmlApplicationContext, TockenService tockenService) throws Exception {
        EntityProxyFactory entityProxyFactory = (EntityProxyFactory) classPathXmlApplicationContext.getBean("entityProxyFactory");
        Tocken tocken = new Tocken();
        Tocken proxyTocken = entityProxyFactory.createProxyEntity(tocken);
        proxyTocken.setUserId(TestConstants.userId);
        proxyTocken.setStatus("测试列表插入" + TestConstants.batchStart);
        List<Tocken> orderList = tockenService.getEntityList(proxyTocken);
        System.out.println(orderList);
        return orderList;
    }

    public static TockenService getTockenProxyService(ClassPathXmlApplicationContext classPathXmlApplicationContext) throws Exception {
        TockenService tockenService = (TockenService) classPathXmlApplicationContext.getBean("tockenService");
        EntityServiceProxyFactory entityServiceProxyFactory = (EntityServiceProxyFactory) classPathXmlApplicationContext.getBean("entityServiceProxyFactory");
        tockenService = entityServiceProxyFactory.createProxyService(tockenService);
        return tockenService;
    }
}
