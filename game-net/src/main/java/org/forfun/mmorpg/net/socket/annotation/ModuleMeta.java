package org.forfun.mmorpg.net.socket.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleMeta {
	
	short module() default 0;

}
