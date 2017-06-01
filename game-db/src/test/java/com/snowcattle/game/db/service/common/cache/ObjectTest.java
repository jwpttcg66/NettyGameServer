package com.snowcattle.game.db.service.common.cache;

import com.snowcattle.game.db.entity.AbstractEntity;
import com.snowcattle.game.db.service.jdbc.entity.Order;

/**
 * Created by jwp on 2017/3/28.
 */
public class ObjectTest {

    public static void main(String[] args) throws InterruptedException {
        int maxSize = 1000000;
        AbstractEntity[] abstractEntity = new AbstractEntity[maxSize];
        for(int i = 0; i < 1000000; i++){
            Order order = new Order();
            order.setId((long)i);
            order.setUserId(10);
            order.setStatus(String.valueOf(i));
            abstractEntity[i] = order;
        }

        Thread.sleep(10000000);
    }
}
