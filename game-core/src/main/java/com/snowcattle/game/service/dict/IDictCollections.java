package com.snowcattle.game.service.dict;

import java.util.Collection;

/**
 * Created by jiangwenping on 17/5/9.
 * 数据字典集合
 */
public interface IDictCollections {
    /**
     * 获取所有数据字典
     * @return
     */
    public Collection<IDict> getAllDicts();
}
