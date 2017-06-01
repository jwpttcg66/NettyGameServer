package com.snowcattle.game.db.service.jdbc.service.operation;

import com.snowcattle.game.db.common.annotation.AsyncEntityOperation;
import com.snowcattle.game.db.service.async.thread.AsyncDbOperation;
import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.service.jdbc.service.entity.impl.OrderService;
import com.snowcattle.game.thread.executor.NonOrderedQueuePoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jwp on 2017/4/12.
 */
@Service
@AsyncEntityOperation(bean = "orderServiceOperation")
public class OrderServiceOperation extends AsyncDbOperation<OrderService> {

    @Autowired
    private OrderService orderService;

    @Override
    public EntityService getWrapperEntityService() {
        return orderService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
