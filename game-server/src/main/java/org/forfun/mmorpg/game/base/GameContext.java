package org.forfun.mmorpg.game.base;

import jakarta.annotation.PostConstruct;
import jforgame.socket.share.message.MessageFactory;
import lombok.Setter;
import org.forfun.mmorpg.game.ServerConfig;
import org.forfun.mmorpg.game.ServerType;
import org.forfun.mmorpg.game.account.AccountService;
import org.forfun.mmorpg.game.asyncdb.AsyncDbService;
import org.forfun.mmorpg.game.buff.service.BuffService;
import org.forfun.mmorpg.game.cross.service.RpcClientRouter;
import org.forfun.mmorpg.game.player.service.PlayerService;
import org.forfun.mmorpg.game.script.ScriptService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

@Component
@Setter(onMethod = @__(@Autowired))
public final class GameContext implements ApplicationContextAware {

    public static ServerType serverType;

    private static GameContext self;

    /**
     * spring容器上下文
     */
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

    public static ServerConfig getServerConfig() {
        return self.serverConfig;
    }

    private AsyncDbService asyncDbService;

    public static AsyncDbService getAysncDbService() {
        return self.asyncDbService;
    }

    private SessionManager sessionManager;

    public static SessionManager getSessionManager() {
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

    private MessageFactory messageFactory;

    public static MessageFactory getMessageFactory() {
        return self.messageFactory;
    }

    private ScriptService scriptService;

    public static ScriptService getScriptService() {
        return self.scriptService;
    }

    private RpcClientRouter rpcClientRouter;

    public static RpcClientRouter getRpcClientRouter() {
        return self.rpcClientRouter;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> Collection<T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz).values();
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return applicationContext.getBeansWithAnnotation(annotationType);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
