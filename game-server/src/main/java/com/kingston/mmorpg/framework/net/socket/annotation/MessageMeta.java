package com.kingston.mmorpg.framework.net.socket.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.kingston.mmorpg.framework.net.socket.message.Message;
import org.springframework.stereotype.Component;

/**
 * The annotation type is used in {@link Message com.kingston.net.Message} to
 * specify module and cmd of the given message.
 * 
 * @author kingston
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MessageMeta {

	byte cmd() default 0;

}
