package com.snowcattle.game.db.common.annotation;

import java.lang.annotation.*;

/**
 * Created by jwp on 2017/3/24.
 * 映射到mybatis db
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DbMapper {
    /**
     * @return
     */
    Class mapper();
}
