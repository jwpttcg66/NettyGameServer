package com.snowcattle.game.common.annotation;

import java.lang.annotation.*;

/**
 * Created by jiangwenping on 2017/5/22.
 * 全局事件监听器注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GlobalEventListenerAnnotation {
}