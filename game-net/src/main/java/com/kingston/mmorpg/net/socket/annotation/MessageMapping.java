package com.kingston.mmorpg.net.socket.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A method whose type is meta-annotated with this is used to be a logic handler
 * 申明协议的执行方法（可选注解）
 * @author kingston
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface MessageMapping {

}
