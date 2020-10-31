package com.kingston.mmorpg.game.database.config.inject;

import com.kingston.mmorpg.game.database.config.container.ConfigCommonValueContainer;
import com.kingston.mmorpg.game.database.config.domain.ConfigCommonValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class CommonValueAutoInjectHandler {

    @Autowired
    private ConfigCommonValueContainer container;

    @Autowired()
    @Qualifier("gameConversion")
    private ConversionService conversionService;

    public Object postBeanAfterInject(Object bean) {
        if (needInject(bean)) {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                CommonValueInject annotation = field.getAnnotation(CommonValueInject.class);
                if (annotation != null) {
                    String dbName = StringUtils.isEmpty(annotation.alias()) ? field.getName() : annotation.alias();
                    ConfigCommonValue commonValue = container.queryOne(dbName);
                    if (commonValue == null) {
                        new IllegalStateException(bean.getClass().getSimpleName() + " commonValue为空,key =" + field.getName());
                    }
                    Object fileValue = conversionService.convert(commonValue.getValue(), field.getType());
                    ReflectionUtils.makeAccessible(field);
                    try {
                        field.set(bean, fileValue);
                    } catch (IllegalAccessException ignore) {
                        //
                    }
                }
            }
        }
        return bean;
    }

    private boolean needInject(Object bean) {
        AtomicBoolean result = new AtomicBoolean(false);
        ReflectionUtils.doWithFields(bean.getClass(), field -> {
            if (field.getAnnotation(CommonValueInject.class) != null) {
                result.set(true);
            }
        });

        return result.get();
    }
}
