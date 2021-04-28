package org.forfun.mmorpg.data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

interface IndexMeta {

    String getName();

    Object getValue(Object obj);
}

class FieldIndexMeta implements IndexMeta {

    private final Field field;

    FieldIndexMeta(Field field) {
        this.field = field;
        this.field.setAccessible(true);
    }

    @Override
    public String getName() {
        return field.getName();
    }

    @Override
    public Object getValue(Object obj) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new IllegalStateException(obj.getClass().getName() + "无法访问" + field.getName() + "字段");
        }
    }
}

class MethodIndexMeta implements IndexMeta {

    private final Method method;

    MethodIndexMeta(Method method) {
        this.method = method;
        this.method.setAccessible(true);
    }

    @Override
    public String getName() {
        return method.getName();
    }

    @Override
    public Object getValue(Object obj) {
        try {
            return method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(obj.getClass().getName() + "无法访问" + method.getName() + "方法");
        }
    }
}
