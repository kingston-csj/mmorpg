package com.kingston.mmorpg.net.socket.annotation;

import com.kingston.mmorpg.net.socket.message.Message;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation type is used in {@link Message com.kingston.net.Message} to
 * specify module and cmd of the given message.
 * 
 * @author kingston
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageMeta {

	byte cmd() default 0;

}
