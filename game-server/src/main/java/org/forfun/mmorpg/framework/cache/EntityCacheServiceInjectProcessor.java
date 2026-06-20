package org.forfun.mmorpg.framework.cache;


import org.forfun.mmorpg.game.database.user.BaseEntity;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Component
public class EntityCacheServiceInjectProcessor implements BeanPostProcessor, ApplicationContextAware, Ordered {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private EntityCacheServiceFactory factory;


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        // 处理 @EntityCacheAutowired 注解的字段
        ReflectionUtils.doWithFields(targetClass, field -> {
            if (field.isAnnotationPresent(EntityCacheAutowired.class) &&
                    BaseEntityCacheService.class.isAssignableFrom(field.getType())) {

                Class<? extends BaseEntity<?>> entityClass = extractEntityClass(field);
                // 生成 Bean 名称
                String serviceBeanName = entityClass.getSimpleName() + "CacheService";

                // 注册为 Spring Bean（如果不存在）
                if (!applicationContext.containsBean(serviceBeanName)) {
                    registerServiceAsBean(entityClass, serviceBeanName);
                }
                // 从容器获取服务 Bean
                BaseEntityCacheService<?, ?> service =
                        applicationContext.getBean(serviceBeanName, BaseEntityCacheService.class);

                // 注入服务
                ReflectionUtils.makeAccessible(field);
                field.set(bean, service);
            }
        });

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 从字段的泛型类型中提取实体类
     *
     * @param field 带有@EntityCacheAutowired注解的字段
     * @return 实体类
     */
    private Class<? extends BaseEntity<?>> extractEntityClass(Field field) {
        Type genericType = field.getGenericType();

        if (genericType instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = paramType.getActualTypeArguments();

            if (actualTypeArguments.length > 0) {
                Type entityType = actualTypeArguments[0];
                if (entityType instanceof Class) {
                    @SuppressWarnings("unchecked")
                    Class<? extends BaseEntity<?>> entityClass = (Class<? extends BaseEntity<?>>) entityType;
                    return entityClass;
                }
            }
        }

        throw new BeanCreationException("无法从字段 " + field.getName() + " 中提取实体类类型");
    }

    /**
     * 将缓存服务注册为Spring Bean
     *
     * @param entityClass 实体类
     * @param beanName    Bean名称
     */
    private void registerServiceAsBean(Class<? extends BaseEntity<?>> entityClass, String beanName) {
        // 使用FactoryBean创建Bean，让Spring管理生命周期
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(EntityCacheServiceFactoryBean.class);
        builder.addConstructorArgValue(entityClass);

        // 注册BeanDefinition
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    }
}