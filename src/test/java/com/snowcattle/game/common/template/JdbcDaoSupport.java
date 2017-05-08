package com.snowcattle.game.common.template;

import java.lang.reflect.ParameterizedType;

/**
 * Created by jiangwenping on 17/4/10.
 */

public abstract class JdbcDaoSupport<T> {

    private Class<T> clazz;

    @SuppressWarnings("unchecked")
    protected JdbcDaoSupport() {
        Class classes = getClass();
        clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println(clazz.getSimpleName());
    }
}