package com.snowcattle.game.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageCommandAnnotation {
	/**
	 * @return
	 */
	int command();
}
