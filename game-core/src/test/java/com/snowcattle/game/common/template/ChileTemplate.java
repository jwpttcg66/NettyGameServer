package com.snowcattle.game.common.template;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by jiangwenping on 17/4/10.
 */
public class ChileTemplate extends SuperTemplate<String> {

    public ChileTemplate() {

    }

    public Class getTClass(int index) {

//        Type[] types = getClass().getGenericInterfaces();
//        Class<?>[] classes = getClass().getInterfaces();
        Type genType = getClass().getGenericSuperclass();

        if (!(genType instanceof ParameterizedType))
        {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }

        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}
