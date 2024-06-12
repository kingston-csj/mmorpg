package org.forfun.mmorpg.game.database.config.inject;

import jakarta.annotation.PostConstruct;
import org.forfun.mmorpg.game.database.config.container.ConfigCommonValueContainer;
import org.forfun.mmorpg.game.database.config.domain.ConfigCommonValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class CommonValueAutoInjectHandler {

    @Autowired
    private ConfigCommonValueContainer container;

    @Autowired()
    @Qualifier("gameConversion")
    private ConversionService conversionService;

    private Map<Class<?>, ConfigValueParser> parserTable = new HashMap<>();

    @PostConstruct
    private void init() {
        parserTable.put(NullInject.class, new NullInject());
        parserTable.put(IntArrayConfigValueParser.class, new IntArrayConfigValueParser());
    }

    public Object postBeanAfterInject(Object bean) {
        if (needInject(bean)) {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                CommonValueInject annotation = field.getAnnotation(CommonValueInject.class);
                if (annotation != null) {
                    ReflectionUtils.makeAccessible(field);

                    String dbName = StringUtils.isEmpty(annotation.alias()) ? field.getName() : annotation.alias();
                    ConfigCommonValue commonValue = container.queryOne(dbName);
                    if (commonValue == null) {
                       throw  new IllegalStateException(bean.getClass().getSimpleName() + " commonValue为空,key =" + field.getName());
                    }
                    Object property = commonValue.getValue();
                    if (annotation.parser() != NullInject.class) {
                        ConfigValueParser parser = parserTable.get(annotation.parser());
                        property = parser.convert((String) property);
                        try {
                            field.set(bean, property);
                        } catch (IllegalAccessException ignore) {
                            //
                        }
                    } else {
                        Object fileValue = conversionService.convert(commonValue.getValue(), field.getType());
                        try {
                            field.set(bean, fileValue);
                        } catch (IllegalAccessException ignore) {
                            //
                        }
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
