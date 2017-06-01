package com.snowcattle.game.db.service.jdbc.mapper;

import com.snowcattle.game.db.entity.IEntity;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangwenping on 17/3/21.
 * 基础mapper
 */
public interface IDBMapper<T extends IEntity> {
    public long insertEntity(T entity);
    public IEntity getEntity(T entity);
    public List<T> getEntityList(T entity);
    public List<T> getEntityList(T entity, RowBounds rowBounds);

    /**
     * 直接查找db，无缓存
     * @param map
     * @return
     */
    public List<T> filterList(Map map);

    /**
     * 直接查找db，无缓存
     * @param map
     * @return
     */
    public List<T> filterList(Map map, RowBounds rowBounds);

    public void updateEntityByMap(Map map);
    public void deleteEntity(T entity);
}
