package com.snowcattle.game.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by jiangwenping on 2017/5/22.
 * 全局事件监听器注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface GlobalEventListenerAnnotation {
}