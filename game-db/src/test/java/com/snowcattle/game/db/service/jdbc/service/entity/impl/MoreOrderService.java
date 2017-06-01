package com.snowcattle.game.db.service.jdbc.service.entity.impl;

import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.service.jdbc.entity.MoreOrder;
import com.snowcattle.game.db.service.jdbc.service.entity.IMoreOrderService;
import com.snowcattle.game.db.sharding.EntityServiceShardingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jiangwenping on 17/4/5.
 */
@Service
public class MoreOrderService extends EntityService<MoreOrder> implements IMoreOrderService {
    @Override
    public EntityServiceShardingStrategy getEntityServiceShardingStrategy() {
        return getDefaultEntityServiceShardingStrategy();
    }

    @Override
    public List<MoreOrder> getMoreOrderList(MoreOrder moreOrder) {
        return getEntityList(moreOrder);
    }

    @Override
    public void insertMoreOrderList(List<MoreOrder> orderList) {
        insertEntityBatch(orderList);
    }

    @Override
    public long insertMoreOrder(MoreOrder moreOrder) {
        return insertEntity(moreOrder);
    }

    @Override
    public MoreOrder getMoreOrder(long userId, long id) {
        MoreOrder moreOrder = new MoreOrder();
        moreOrder.setUserId(userId);
        moreOrder.setId(id);
        return (MoreOrder) getEntity(moreOrder);
    }

    @Override
    public List<MoreOrder> getMoreOrderList(long userId) {
        MoreOrder moreOrder = new MoreOrder();
        moreOrder.setUserId(userId);
        return getEntityList(moreOrder);
    }

    @Override
    public List<MoreOrder> getMoreOrderList(long userId, String status) {
        MoreOrder moreOrder = new MoreOrder();
        moreOrder.setUserId(userId);
        moreOrder.setStatus(status);
        return getEntityList(moreOrder);
    }


    @Override
    public void updateMoreOrder(MoreOrder order) {
        updateEntity(order);
    }

    @Override
    public void deleteMoreOrder(MoreOrder order) {
        deleteEntity(order);
    }

    @Override
    public void updateMoreOrderList(List<MoreOrder> orderList) {
        updateEntityBatch(orderList);
    }

    @Override
    public void deleteMoreOrderList(List<MoreOrder> orderList) {
        deleteEntityBatch(orderList);
    }
}
