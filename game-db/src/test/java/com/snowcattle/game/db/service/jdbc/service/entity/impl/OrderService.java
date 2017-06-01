package com.snowcattle.game.db.service.jdbc.service.entity.impl;

import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.service.jdbc.entity.Order;
import com.snowcattle.game.db.service.jdbc.service.entity.IOrderService;
import com.snowcattle.game.db.sharding.EntityServiceShardingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangwenping on 17/3/20.
 */
@Service
public class OrderService extends EntityService<Order> implements IOrderService{


    public long insertOrder(Order order) {
        return insertEntity(order);
    }

    @Override
    public Order getOrder(long userId, long id) {
        Order order = new Order();
        order.setUserId(userId);
        order.setId(id);
        return (Order) getEntity(order);
    }

    @Override
    public List<Order> getOrderList(long userId) {
        Order order = new Order();
        order.setUserId(userId);
        return getEntityList(order);
    }

    @Override
    public List<Order> getOrderList(long userId, String status) {
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(status);
        return getEntityList(order);
    }

    @Override
    public void updateOrder(Order order) {
        updateEntity(order);
    }

    @Override
    public void deleteOrder(Order order) {
        deleteEntity(order);
    }

    @Override
    public List<Long> insertOrderList(List<Order> order) {
        return insertEntityBatch(order);
    }

    @Override
    public void updateOrderList(List<Order> order) {
        updateEntityBatch(order);
    }

    @Override
    public void deleteOrderList(List<Order> order) {
        deleteEntityBatch(order);
    }

    @Override
    public EntityServiceShardingStrategy getEntityServiceShardingStrategy() {
        return getDefaultEntityServiceShardingStrategy();
    }
}
