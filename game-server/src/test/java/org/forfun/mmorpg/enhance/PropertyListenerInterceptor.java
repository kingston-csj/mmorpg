package org.forfun.mmorpg.enhance;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyListenerInterceptor implements MethodInterceptor {

    private PropertyChangeSupport support;

    public void binding(Object target) {
        support = new PropertyChangeSupport(target);
        support.addPropertyChangeListener(evt ->
                        System.out.println(String.format("对象 [%s]属性发生变更，从%s变为%s",
                                evt.getPropertyName(), evt.getOldValue(), evt.getNewValue()))
                );
    }

    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if (method.getName().startsWith("set")) {
            String fieldName = method.getName().replace("set", "");
            fieldName = fieldName.substring(0,1).toLowerCase() + fieldName.substring(1);
            Field field = ReflectionUtils.findField(target.getClass(), fieldName);
            field.setAccessible(true);
            Object oldValue = field.get(target);
            Object ret = methodProxy.invokeSuper(target, args);
            Object newValue = field.get(target);
            support.firePropertyChange(fieldName, oldValue, newValue);
            return ret;
        } else if (method.getName().startsWith("get")) {
            String fieldName = method.getName().replace("get", "");
            fieldName = fieldName.substring(0,1).toLowerCase() + fieldName.substring(1);
            System.out.println(String.format("访问对象 [%s]属性", fieldName));
        }
        return methodProxy.invokeSuper(target, args);
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Player.class);
        PropertyListenerInterceptor interceptor = new PropertyListenerInterceptor();
        enhancer.setCallback(interceptor);
        Player player = (Player) enhancer.create();
        interceptor.binding(player);
        player.setLevel(10);
        player.setName("Kitty");
        player.getName();
//        程序输出：
//        对象 [level]属性发生变更，从0变为10
//        对象 [name]属性发生变更，从null变为Kitty
//        访问对象 [name]属性
    }
}
