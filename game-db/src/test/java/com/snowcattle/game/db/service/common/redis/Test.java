package com.snowcattle.game.db.service.common.redis;

import com.snowcattle.game.db.service.redis.RedisService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by jiangwenping on 17/4/7.
 * 这个可是用于测试 能否保证insert总是update执行，并且不丢sql数据
 */
public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(new String[]{"bean/*.xml"});
        RedisService redisService = (RedisService) classPathXmlApplicationContext.getBean("redisService");
        String setKey = "set";
        String playerKey = "player";
        redisService.deleteKey(setKey);
        redisService.deleteKey(playerKey);
        RedisPop redisPop = new RedisPop(redisService, setKey, playerKey);
        RedisPush redisPush = new RedisPush(redisService, setKey, playerKey);
        RedisPop redisPop2 = new RedisPop(redisService, setKey, playerKey);
        redisPop.start();
        redisPush.start();
        redisPop2.start();
    }
}
