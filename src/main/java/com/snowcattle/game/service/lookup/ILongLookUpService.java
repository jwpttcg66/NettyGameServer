package com.snowcattle.game.service.lookup;

/**
 * Created by jwp on 2017/2/10.
 * 提供查询服务
 */
public interface ILongLookUpService<T extends ILongId> {

    /**
     * 查找
     * @param Id
     * @return
     */
    public T lookup(long id);

    /**
     * 增加
     * @param t
     */
    public void addT(T t);

    /**
     * 移除
     * @param t
     * @return
     */
    public boolean removeT(T t);
}
