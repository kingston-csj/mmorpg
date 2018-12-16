package com.kingston.mmorpg.framework.net.socket.task;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.annotation.RequestMapping;
import com.kingston.mmorpg.framework.net.socket.message.CmdExecutor;
import com.kingston.mmorpg.framework.net.socket.message.Message;

@Component
public class MessageHandlerBeanPostProcessor 
	implements BeanPostProcessor, ApplicationContextAware, Ordered {

	@Autowired
	private MessageDispatcher messageDispatcher;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		//扫描所有controller的消息处理器

		try {
			Class<?> clz = bean.getClass();
			Method[] methods = clz.getDeclaredMethods();
			for (Method method:methods) {
				RequestMapping mapperAnnotation = method.getAnnotation(RequestMapping.class);
				if (mapperAnnotation != null) {
					short[] meta = getMessageMeta(method);
					if (meta == null) {
						throw new RuntimeException(String.format("controller[%s] method[%s] lack of RequestMapping annotation",
								clz.getName(), method.getName()));
					}
					short module = meta[0];
					short cmd    = meta[1];
					String key = buildKey(module, cmd);

					CmdExecutor cmdExecutor = CmdExecutor.valueOf(method, method.getParameterTypes(), bean);

					messageDispatcher.registerMethodInvoke(key, cmdExecutor);
				}
			}
		}catch(Exception e) {
		}

		return bean;
	}

	/**
	 * 返回方法所带Message参数的元信息
	 * @param method
	 * @return
	 */
	private short[] getMessageMeta(Method method) {
		for (Class<?> paramClazz: method.getParameterTypes()) {
			if (Message.class.isAssignableFrom(paramClazz)) {
				MessageMeta protocol = paramClazz.getAnnotation(MessageMeta.class);
				if (protocol != null) {
					short[] meta = {protocol.module(), protocol.cmd()};
					return meta;
				}
			}
		}
		return null;
	}

	private String buildKey(short module, short cmd) {
		return module + "_" + cmd;
	}


	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
