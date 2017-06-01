package com.snowcattle.game.db.service.jdbc.service.entity;

import com.snowcattle.game.db.service.jdbc.entity.Order;

import java.util.List;

/**
 * Created by jiangwenping on 17/3/20.
 */
public interface IOrderService {
    public long insertOrder(Order order);
    public Order getOrder(long userId, long id);
    public List<Order> getOrderList(long userId);
    public List<Order> getOrderList(long userId, String status);
    void updateOrder(Order order);
    void deleteOrder(Order order);

    public List<Long> insertOrderList(List<Order> order);
    public void updateOrderList(List<Order> order);
    public void deleteOrderList(List<Order> order);
}

