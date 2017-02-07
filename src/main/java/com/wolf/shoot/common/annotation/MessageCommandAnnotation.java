package com.wolf.shoot.common.annotation;

import com.wolf.shoot.net.message.MessageCommands;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageCommandAnnotation {
	MessageCommands command();
}
