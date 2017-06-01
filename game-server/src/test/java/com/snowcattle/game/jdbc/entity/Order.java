package com.snowcattle.game.jdbc.entity;

import com.snowcattle.game.db.common.annotation.DbMapper;
import com.snowcattle.game.db.common.annotation.EntitySave;
import com.snowcattle.game.db.common.annotation.FieldSave;
import com.snowcattle.game.db.common.annotation.MethodSaveProxy;
import com.snowcattle.game.db.entity.BaseLongIDEntity;
import com.snowcattle.game.db.service.redis.RedisInterface;
import com.snowcattle.game.db.util.EntityUtils;
import com.snowcattle.game.jdbc.mapper.OrderMapper;

@EntitySave
@DbMapper(mapper = OrderMapper.class)
public class Order extends BaseLongIDEntity implements RedisInterface {

    @FieldSave
    private String status;

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    @MethodSaveProxy(proxy="status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + getId() +
                ", userId=" + getUserId() +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public String getUnionKey() {
        return String.valueOf(getUserId()+ EntityUtils.ENTITY_SPLIT_STRING + getId());
    }

    @Override
    public String getRedisKeyEnumString() {
        return "od#";
    }
}