package com.snowcattle.game.db.service.jdbc.service.entity.impl;

import com.snowcattle.game.db.service.entity.EntityService;
import com.snowcattle.game.db.service.jdbc.entity.Tocken;
import com.snowcattle.game.db.service.jdbc.service.entity.ITockenService;
import com.snowcattle.game.db.sharding.EntityServiceShardingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sunmosh on 2017/4/5.
 */
@Service
public class TockenService extends EntityService<Tocken> implements ITockenService {
    public long insertTocken(Tocken tocken) {
        return insertEntity(tocken);
    }

    @Override
    public Tocken getTocken(long userId, String id) {
        Tocken tocken = new Tocken();
        tocken.setUserId(userId);
        tocken.setId(id);
        return (Tocken) getEntity(tocken);
    }

    @Override
    public List<Tocken> getTockenList(long userId) {
        Tocken tocken = new Tocken();
        tocken.setUserId(userId);
        return getEntityList(tocken);
    }

    @Override
    public void updateTocken(Tocken tocken) {
        updateEntity(tocken);
    }

    @Override
    public void deleteTocken(Tocken tocken) {
        deleteEntity(tocken);
    }

    @Override
    public EntityServiceShardingStrategy getEntityServiceShardingStrategy() {
        return getDefaultEntityServiceShardingStrategy();
    }

}
