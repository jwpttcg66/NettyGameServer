package com.snowcattle.game.db.service.common.cache;

import com.snowcattle.game.db.service.redis.RedisListInterface;
import com.snowcattle.game.db.service.redis.RedisService;
import com.snowcattle.game.db.service.jdbc.entity.MoreOrder;
import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.proxy.EntityProxyFactory;
import com.snowcattle.game.db.util.EntityUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangwenping on 17/3/20.
 */
public class redisTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        RedisService redisService = (RedisService) classPathXmlApplicationContext.getBean("redisService");
        MoreOrder moreOrder = new MoreOrder();
        moreOrder.setId((long)1);
        moreOrder.setUserId(2);
        moreOrder.setStatus("list");
        List<RedisListInterface> list = new ArrayList<RedisListInterface>();
        list.add(moreOrder);
        redisService.setListToHash(EntityUtils.getRedisKeyByRedisListInterface(moreOrder), list);

        list = redisService.getListFromHash(EntityUtils.getRedisKeyByRedisListInterface(moreOrder), MoreOrder.class);
        System.out.println(list);

    }

    public static void testObject() throws Exception {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        RedisService redisService = (RedisService) classPathXmlApplicationContext.getBean("redisService");
//        redisService.setString("common", "2");
//        System.out.println(redisService.getString("common"));
        Order order = new Order();
        long id = 2;
        long userId = 2;
        String status = "测试";
        order.setId(id);
        order.setUserId(userId);
        order.setStatus(status);
        order.setDeleted(false);
        String key = "common";
        redisService.setObjectToHash(key, order);
        Order queryOrder= redisService.getObjectFromHash(key, Order.class);
        System.out.println(queryOrder);

        EntityProxyFactory entityProxyFactory = new EntityProxyFactory();
        Order proxyOrder = entityProxyFactory.createProxyEntity(queryOrder);
        proxyOrder.setStatus("common");
        redisService.updateObjectHashMap(key, proxyOrder.getEntityProxyWrapper().getEntityProxy().getChangeParamSet());
        key = "od#202";
        queryOrder= redisService.getObjectFromHash(key, Order.class);
        System.out.println(queryOrder);
    }
}
