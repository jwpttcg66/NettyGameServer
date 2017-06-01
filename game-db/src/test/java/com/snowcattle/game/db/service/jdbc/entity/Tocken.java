package com.snowcattle.game.db.service.jdbc.entity;

import com.snowcattle.game.db.service.redis.RedisInterface;
import com.snowcattle.game.db.service.redis.RedisKeyEnum;
import com.snowcattle.game.db.common.annotation.DbMapper;
import com.snowcattle.game.db.common.annotation.EntitySave;
import com.snowcattle.game.db.common.annotation.FieldSave;
import com.snowcattle.game.db.common.annotation.MethodSaveProxy;
import com.snowcattle.game.db.entity.BaseStringIDEntity;
import com.snowcattle.game.db.service.entity.EntityKeyShardingStrategyEnum;
import com.snowcattle.game.db.service.jdbc.mapper.TockenMapper;

/**
 * Created by sunmosh on 2017/4/5.
 */
@EntitySave
@DbMapper(mapper = TockenMapper.class)
public class Tocken extends BaseStringIDEntity implements RedisInterface{

    @Override
    public String getUnionKey() {
        return getId();
    }

    @Override
    public String getRedisKeyEnumString() {
        return RedisKeyEnum.TOCKEN.getKey();
    }

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
        return "Tocken{" +
                "tocken=" + getId() +
                ", userId=" + getUserId() +
                ", status='" + status + '\'' +
                '}';
    }

    public EntityKeyShardingStrategyEnum getEntityKeyShardingStrategyEnum(){
        return EntityKeyShardingStrategyEnum.ID;
    }
}
