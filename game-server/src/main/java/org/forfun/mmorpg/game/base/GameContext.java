package org.forfun.mmorpg.game.base;

import lombok.Setter;
import org.forfun.mmorpg.framework.net.MessageDispatcher;
import org.forfun.mmorpg.game.ServerConfig;
import org.forfun.mmorpg.game.ServerType;
import org.forfun.mmorpg.game.account.AccountService;
import org.forfun.mmorpg.game.asyncdb.AsyncDbService;
import org.forfun.mmorpg.game.buff.service.BuffService;
import org.forfun.mmorpg.game.player.service.PlayerService;
import org.forfun.mmorpg.game.script.ScriptService;
import org.forfun.mmorpg.protocol.codec.SerializerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

@Component
@Setter(onMethod = @__(@Autowired))
public class GameContext implements ApplicationContextAware {

	public static ServerType serverType;

	private static GameContext self;

	/** spring容器上下文 */
	private static ApplicationContext applicationContext = null;

	@PostConstruct
	private void init() {
		self = this;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		GameContext.applicationContext = applicationContext;
	}

	public ServerConfig serverConfig;

	public final static ServerConfig getServerConfig() {
		return self.serverConfig;
	}

	private AsyncDbService asyncDbService;

	public final static AsyncDbService getAysncDbService() {
		return self.asyncDbService;
	}

	private SessionManager sessionManager;

	public final static SessionManager getSessionManager() {
		return self.sessionManager;
	}

	private PlayerService playerService;

	public static PlayerService getPlayerService() {
		return self.playerService;
	}

	private AccountService accountService;

	public static AccountService getAccountService() {
		return self.accountService;
	}

	private SchedulerManager schedulerManager;

	public static SchedulerManager getSchedulerManager() {
		return self.schedulerManager;
	}

	private BuffService buffService;

	public static BuffService getBuffService() {
		return self.buffService;
	}

	private MessageDispatcher messageDispatcher;

	public static MessageDispatcher getMessageDispatcher() {
		return self.messageDispatcher;
	}

	private SerializerFactory messageSerializer;

	public static SerializerFactory getMessageSerializer() {
		return self.messageSerializer;
	}

	private ScriptService scriptService;

	public static ScriptService getScriptService() {
		return self.scriptService;
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

	public final static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
		return applicationContext.getBeansWithAnnotation(annotationType);
	}

}
