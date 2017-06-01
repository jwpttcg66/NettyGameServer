package com.snowcattle.game.db.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jiangwenping on 17/3/16.
 * 类需要使用代理 会通过此注解，注入到代理对象的变化集合里
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodSaveProxy {
    /**
     * @return
     */
    String proxy();
}
