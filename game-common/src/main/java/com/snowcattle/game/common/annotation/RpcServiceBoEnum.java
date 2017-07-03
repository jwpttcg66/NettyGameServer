package com.snowcattle.game.common.annotation;

import com.snowcattle.game.common.enums.BOEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jiangwenping on 17/4/26.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcServiceBoEnum {
    BOEnum bo();
}
