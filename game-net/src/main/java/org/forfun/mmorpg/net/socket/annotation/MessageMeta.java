package org.forfun.mmorpg.net.socket.annotation;

import org.forfun.mmorpg.net.message.Message;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation type is used in {@link Message} to
 * specify module and cmd of the given message.
 * 
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageMeta {

	byte cmd() default 0;

}
