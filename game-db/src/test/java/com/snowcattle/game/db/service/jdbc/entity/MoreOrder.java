package com.snowcattle.game.db.service.jdbc.entity;

import com.snowcattle.game.db.common.annotation.DbMapper;
import com.snowcattle.game.db.common.annotation.EntitySave;
import com.snowcattle.game.db.common.annotation.FieldSave;
import com.snowcattle.game.db.common.annotation.MethodSaveProxy;
import com.snowcattle.game.db.entity.BaseLongIDEntity;
import com.snowcattle.game.db.service.jdbc.mapper.MoreOrderMapper;
import com.snowcattle.game.db.service.redis.RedisListInterface;

/**
 * Created by jwp on 2017/3/24.
 * 实体
 */
@EntitySave
@DbMapper(mapper = MoreOrderMapper.class)
public class MoreOrder  extends BaseLongIDEntity implements RedisListInterface{


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
    public String getShardingKey() {
        return String.valueOf(getUserId());
    }

    @Override
    public String getSubUniqueKey() {
        return String.valueOf(getId());
    }

    @Override
    public String getRedisKeyEnumString() {
        return "mod#";
    }
}
