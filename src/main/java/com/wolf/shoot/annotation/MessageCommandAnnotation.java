package com.wolf.shoot.annotation;

import com.wolf.shoot.net.message.MessageCommands;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageCommandAnnotation {
	MessageCommands command();
}
