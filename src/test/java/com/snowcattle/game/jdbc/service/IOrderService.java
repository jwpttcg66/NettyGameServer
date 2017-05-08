package com.snowcattle.game.jdbc.service;


import java.util.List;

import com.snowcattle.game.jdbc.entity.Order;

/**
 * Created by jiangwenping on 17/3/20.
 */
public interface IOrderService {
    public long insertOrder(Order order);
    public Order getOrder(long userId, long id);
    public List<Order> getOrderList(long userId);
    void updateOrder(Order order);
    void deleteOrder(Order order);

    public List<Long> insertOrderList(List<Order> order);
    public void updateOrderList(List<Order> order);
    public void deleteOrderList(List<Order> order);
}

