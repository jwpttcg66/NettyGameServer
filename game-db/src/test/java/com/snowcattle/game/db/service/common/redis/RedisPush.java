package com.snowcattle.game.db.service.common.redis;

import com.snowcattle.game.db.service.redis.RedisService;

/**
 * Created by jiangwenping on 17/4/7.
 */
public class RedisPush extends Thread{

    RedisService redisService;
    private String setKey;
    private String listKey;

    public RedisPush(RedisService redisService, String setKey, String listKey) {
        this.redisService = redisService;
        this.setKey = setKey;
        this.listKey = listKey;
    }

    @Override
    public void run() {
        int i = 1100;
        while(i<1500){
            redisService.rPushString(listKey, String.valueOf(i));
            System.out.println("插入"  + listKey  + "值" + String.valueOf(i));
            redisService.rPushString(listKey, String.valueOf(i + 2000));
            System.out.println("更新" + listKey  + "值" + String.valueOf(i + 2000));
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            redisService.saddString(setKey, listKey);


            redisService.rPushString(listKey, String.valueOf(i + 4000));
            System.out.println("更新" + listKey  + "值" + String.valueOf(i + 4000));
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            redisService.saddString(setKey, listKey);
            i++;

        }
    }
}
