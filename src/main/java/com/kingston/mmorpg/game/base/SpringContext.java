package com.kingston.mmorpg.game.base;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.kingston.mmorpg.framework.net.socket.MessageFactory;
import com.kingston.mmorpg.framework.net.socket.task.MessageDispatcher;
import com.kingston.mmorpg.game.ServerConfig;
import com.kingston.mmorpg.game.buff.service.BuffService;
import com.kingston.mmorpg.game.player.service.PlayerService;

public class SpringContext implements ApplicationContextAware {
	
	private static SpringContext self;
	
	/** spring容器上下文 */
	private static ApplicationContext applicationContext = null;
	
	@PostConstruct
	private void init() {
		self = this;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContext.applicationContext = applicationContext;
	}
	
	@Resource
	private MessageFactory messageFactory;
	
	public final static MessageFactory getMessageFactory() {
		return self.messageFactory;
	}
	
	@Resource
	private ServerConfig serverConfig;
	
	public final static ServerConfig getServerConfig() {
		return self.serverConfig;
	}
	
	@Resource
	private SessionManager sessionManager;
	
	public final static SessionManager getSessionManager() {
		return self.sessionManager;
	}
	
	@Resource
	private PlayerService playerService;
	
	public static PlayerService getPlayerService() {
		return self.playerService;
	}
	
	@Resource
	private SchedulerManager schedulerManager;
	
	public static SchedulerManager getSchedulerManager() {
		return self.schedulerManager;
	}
	
	@Autowired
	private BuffService buffService;
	
	public static BuffService getBuffService() {
		return self.buffService;
	}
	
	@Autowired
	private MessageDispatcher messageDispatcher;
	
	public static MessageDispatcher getMessageDispatcher() {
		return self.messageDispatcher;
	}
	
	public final static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	public final static <T> Collection<T> getBeansOfType(Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz).values();
	}
	
	public final static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

}
