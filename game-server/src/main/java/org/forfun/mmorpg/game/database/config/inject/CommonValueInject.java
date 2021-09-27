package org.forfun.mmorpg.game.database.config.inject;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonValueInject {

    /**
     * 配置表字段别名，允许程序字段与策划配置名字不一样
     * @return
     */
    String alias() default "";

    Class<? extends ConfigValueParser> parser() default NullInject.class;
}
