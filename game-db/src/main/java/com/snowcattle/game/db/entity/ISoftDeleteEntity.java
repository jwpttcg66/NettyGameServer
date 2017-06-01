package com.snowcattle.game.db.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jiangwenping on 17/3/16.
 * 所有对象都是先设置删除时间，删除标志，设置缓存，然后同步db异步操作执行删除后，执行回调，然后删除缓存
 * 缓存获取对象的时候，需要过滤已经删除的对象
 */
public interface ISoftDeleteEntity<ID extends Serializable> extends IEntity<ID> {
    public boolean isDeleted();
    public void setDeleted(boolean deleted);
    public Date getDeleteTime();
    public void setDeleteTime(Date deleteTime);
}
