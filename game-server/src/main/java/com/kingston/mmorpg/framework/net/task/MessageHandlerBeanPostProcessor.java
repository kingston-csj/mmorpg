package com.kingston.mmorpg.framework.net.task;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMapping;
import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.annotation.ModuleMeta;
import com.kingston.mmorpg.framework.net.socket.message.CmdExecutor;
import com.kingston.mmorpg.framework.net.socket.message.Message;

@Component
public class MessageHandlerBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware, Ordered {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageDispatcher messageDispatcher;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		try {
			Class<?> clz = bean.getClass();
			ModuleMeta moduleMeta = clz.getAnnotation(ModuleMeta.class);
			if (moduleMeta == null) {
				return bean;
			}
			short module = moduleMeta.module();
			Method[] methods = clz.getDeclaredMethods();
			for (Method method : methods) {
				MessageMapping mapperAnnotation = method.getAnnotation(MessageMapping.class);
				if (mapperAnnotation != null) {
					short cmdMeta = getMessageMeta(method);
					if (cmdMeta <= 0) {
						throw new RuntimeException(
								String.format("controller[%s] method[%s] lack of RequestMapping annotation",
										clz.getName(), method.getName()));
					}
					short key = buildKey(module, cmdMeta);

					CmdExecutor cmdExecutor = CmdExecutor.valueOf(method, method.getParameterTypes(), bean);
					messageDispatcher.registerMethodInvoke(key, cmdExecutor);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}

		return bean;
	}

	/**
	 * 返回方法所带Message参数的元信息
	 * 
	 * @param method
	 * @return
	 */
	private short getMessageMeta(Method method) {
		for (Class<?> paramClazz : method.getParameterTypes()) {
			if (Message.class.isAssignableFrom(paramClazz)) {
				MessageMeta protocol = paramClazz.getAnnotation(MessageMeta.class);
				if (protocol != null) {
					return protocol.cmd();
				}
			}
		}
		return 0;
	}

	private short buildKey(short module, short cmd) {
		short key = (short)(Math.abs(module) * 100 + cmd);
		if (module < 0) {
			key = (short) (0 - key);
		}
		return key;
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
