package org.forfun.mmorpg.game.base;

import jakarta.annotation.PostConstruct;
import jforgame.commons.eventbus.EventBus;
import jforgame.data.DataManager;
import jforgame.socket.share.message.MessageFactory;
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

    private ServerConfig serverConfig;

    public static ServerConfig getServerConfig() {
        return self.serverConfig;
    }

    @Autowired
    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    private EventBus eventBus;

    public static EventBus getEventBus() {
        return self.eventBus;
    }

    @Autowired
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    private AsyncDbService asyncDbService;

    public static AsyncDbService getAysncDbService() {
        return self.asyncDbService;
    }

    @Autowired
    public void setAsyncDbService(AsyncDbService asyncDbService) {
        this.asyncDbService = asyncDbService;
    }

    private SessionManager sessionManager;

    public static SessionManager getSessionManager() {
        return self.sessionManager;
    }

    @Autowired
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    private DataManager dataManager;

    public static DataManager getDataManager() {
        return self.dataManager;
    }

    @Autowired
    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    private PlayerService playerService;

    public static PlayerService getPlayerService() {
        return self.playerService;
    }

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    private AccountService accountService;

    public static AccountService getAccountService() {
        return self.accountService;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    private SchedulerManager schedulerManager;

    public static SchedulerManager getSchedulerManager() {
        return self.schedulerManager;
    }

    @Autowired
    public void setSchedulerManager(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    private BuffService buffService;

    public static BuffService getBuffService() {
        return self.buffService;
    }

    @Autowired
    public void setBuffService(BuffService buffService) {
        this.buffService = buffService;
    }

    private MessageFactory messageFactory;

    public static MessageFactory getMessageFactory() {
        return self.messageFactory;
    }

    @Autowired
    public void setMessageFactory(MessageFactory messageFactory) {
        this.messageFactory = messageFactory;
    }

    private ScriptService scriptService;

    public static ScriptService getScriptService() {
        return self.scriptService;
    }

    @Autowired
    public void setScriptService(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    private RpcClientRouter rpcClientRouter;

    public static RpcClientRouter getRpcClientRouter() {
        return self.rpcClientRouter;
    }

    @Autowired
    public void setRpcClientRouter(RpcClientRouter rpcClientRouter) {
        this.rpcClientRouter = rpcClientRouter;
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
