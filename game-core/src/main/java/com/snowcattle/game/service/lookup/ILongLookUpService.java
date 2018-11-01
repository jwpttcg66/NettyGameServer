package com.snowcattle.game.service.lookup;

/**
 * Created by jwp on 2017/2/10.
 * 提供查询服务
 */
public interface ILongLookUpService<T extends ILongId> {

    /**
     * 查找
     * @param id
     * @return
     */
    T lookup(long id);

    /**
     * 增加
     * @param t
     */
    void addT(T t);

    /**
     * 移除
     * @param t
     * @return
     */
    boolean removeT(T t);

    /**
     * 清除所有
     */
    void clear();
}
