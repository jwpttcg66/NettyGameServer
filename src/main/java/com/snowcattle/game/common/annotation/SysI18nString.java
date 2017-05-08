package com.snowcattle.game.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统提示用多语言
 *
 *
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SysI18nString {
	/**
	 * 多语言内容
	 */
	String content();

	/**
	 * 多语言参数注释
	 */
	String comment() default "";
}
