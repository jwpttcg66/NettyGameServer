package com.snowcattle.game.db.common.annotation;

import com.snowcattle.game.db.common.enums.DbOperationEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jwp on 2017/3/23.
 * 数据存储操作
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DbOperation {
    /**
     * @return
     */
    DbOperationEnum operation();
}

