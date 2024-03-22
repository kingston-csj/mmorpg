//package org.forfun.mmorpg.framework.net;
//
//
//import jforgame.socket.share.annotation.MessageRoute;
//import jforgame.socket.share.message.MessageExecutor;
//import jforgame.socket.support.MessageExecuteUnit;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
//@Component
//public class MessageHandlerBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware, Ordered {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    private MessageIoDispatcher messageDispatcher;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//    }
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        try {
//            Class<?> clz = bean.getClass();
//            MessageRoute moduleMeta = clz.getAnnotation(MessageRoute.class);
//            if (moduleMeta == null) {
//                return bean;
//            }
//            short module = moduleMeta.module();
//            Method[] methods = clz.getDeclaredMethods();
//            for (Method method : methods) {
//                if (isRequestMapperMethod(method)) {
//                    byte cmdMeta = getMessageMeta(method);
//                    if (cmdMeta <= 0) {
//                        throw new RuntimeException(
//                                String.format("controller[%s] method[%s] lack of RequestMapping annotation",
//                                        clz.getName(), method.getName()));
//                    }
//                    short key = buildKey(module, cmdMeta);
//                    MessageExecutor cmdExecutor = MessageExecuteUnit.valueOf(method, method.getParameterTypes(), bean);
//                    messageDispatcher.registerMethodInvoke(key, cmdExecutor);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("", e);
//        }
//
//        return bean;
//    }
//
//    /**
//     * 参数属于{@link Message}则代表是客户端请求映射方法
//     *
//     * @param method
//     * @return
//     */
//    private boolean isRequestMapperMethod(Method method) {
//        Class<?>[] parameters = method.getParameterTypes();
//        for (Class<?> parameter : parameters) {
//            if (Message.class.isAssignableFrom(parameter)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 返回方法所带Message参数的元信息
//     *
//     * @param method
//     * @return
//     */
//    private byte getMessageMeta(Method method) {
//        for (Class<?> paramClazz : method.getParameterTypes()) {
//            if (Message.class.isAssignableFrom(paramClazz)) {
//                MessageMeta protocol = paramClazz.getAnnotation(MessageMeta.class);
//                if (protocol != null) {
//                    return protocol.cmd();
//                }
//            }
//        }
//        return 0;
//    }
//
//    private short buildKey(short module, byte cmd) {
//        short key = (short) (Math.abs(module) * 100 + cmd);
//        if (module < 0) {
//            key = (short) (-key);
//        }
//        return key;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        return bean;
//    }
//
//    @Override
//    public int getOrder() {
//        return Integer.MIN_VALUE;
//    }
//
//}