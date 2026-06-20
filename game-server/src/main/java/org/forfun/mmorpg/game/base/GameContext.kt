package org.forfun.mmorpg.game.base


import jforgame.commons.eventbus.EventBus
import jforgame.data.DataManager
import jforgame.socket.share.message.MessageFactory
import org.forfun.mmorpg.game.ServerConfig
import org.forfun.mmorpg.game.ServerType
import org.forfun.mmorpg.game.account.AccountService
import org.forfun.mmorpg.game.asyncdb.AsyncDbService
import org.forfun.mmorpg.game.buff.service.BuffService
import org.forfun.mmorpg.game.cross.service.RpcClientRouter
import org.forfun.mmorpg.game.player.service.PlayerService
import org.forfun.mmorpg.game.script.ScriptService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class GameContext private constructor() : ApplicationContextAware {

    companion object {
        @JvmField
        var serverType: ServerType? = null

        private lateinit var self: GameContext

        private var applicationContext: ApplicationContext? = null

        @JvmStatic
        fun setSelf(context: GameContext) {
            self = context
        }

        @JvmStatic
        fun getServerConfig(): ServerConfig? = self.serverConfig

        @JvmStatic
        fun getEventBus(): EventBus? = self.eventBus

        @JvmStatic
        fun getAysncDbService(): AsyncDbService? = self.asyncDbService

        @JvmStatic
        fun getSessionManager(): SessionManager? = self.sessionManager

        @JvmStatic
        fun getDataManager(): DataManager? = self.dataManager

        @JvmStatic
        fun getPlayerService(): PlayerService? = self.playerService

        @JvmStatic
        fun getAccountService(): AccountService? = self.accountService

        @JvmStatic
        fun getSchedulerManager(): SchedulerManager? = self.schedulerManager

        @JvmStatic
        fun getBuffService(): BuffService? = self.buffService

        @JvmStatic
        fun getMessageFactory(): MessageFactory? = self.messageFactory

        @JvmStatic
        fun getScriptService(): ScriptService? = self.scriptService

        @JvmStatic
        fun getRpcClientRouter(): RpcClientRouter? = self.rpcClientRouter

        @JvmStatic
        fun <T : Any> getBean(clazz: Class<T>): T {
            return applicationContext!!.getBean(clazz)
        }

        @JvmStatic
        fun <T : Any> getBeansOfType(clazz: Class<T>): Collection<T> {
            return applicationContext!!.getBeansOfType(clazz).values
        }

        @JvmStatic
        fun <T : Any> getBean(name: String, requiredType: Class<T>): T {
            return applicationContext!!.getBean(name, requiredType)
        }

        @JvmStatic
        fun getBeansWithAnnotation(annotationType: Class<out Annotation>): Map<String, Any> {
            return applicationContext!!.getBeansWithAnnotation(annotationType)
        }

        @JvmStatic
        fun getApplicationContext(): ApplicationContext? {
            return applicationContext
        }
    }

    private var serverConfig: ServerConfig? = null

    @Autowired
    fun setServerConfig(serverConfig: ServerConfig) {
        this.serverConfig = serverConfig
    }

    private var eventBus: EventBus? = null

    @Autowired
    fun setEventBus(eventBus: EventBus) {
        this.eventBus = eventBus
    }

    private var asyncDbService: AsyncDbService? = null

    @Autowired
    fun setAsyncDbService(asyncDbService: AsyncDbService) {
        this.asyncDbService = asyncDbService
    }

    private var sessionManager: SessionManager? = null

    @Autowired
    fun setSessionManager(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }

    private var dataManager: DataManager? = null

    @Autowired
    fun setDataManager(dataManager: DataManager) {
        this.dataManager = dataManager
    }

    private var playerService: PlayerService? = null

    @Autowired
    fun setPlayerService(playerService: PlayerService) {
        this.playerService = playerService
    }

    private var accountService: AccountService? = null

    @Autowired
    fun setAccountService(accountService: AccountService) {
        this.accountService = accountService
    }

    private var schedulerManager: SchedulerManager? = null

    @Autowired
    fun setSchedulerManager(schedulerManager: SchedulerManager) {
        this.schedulerManager = schedulerManager
    }

    private var buffService: BuffService? = null

    @Autowired
    fun setBuffService(buffService: BuffService) {
        this.buffService = buffService
    }

    private var messageFactory: MessageFactory? = null

    @Autowired
    fun setMessageFactory(messageFactory: MessageFactory) {
        this.messageFactory = messageFactory
    }

    private var scriptService: ScriptService? = null

    @Autowired
    fun setScriptService(scriptService: ScriptService) {
        this.scriptService = scriptService
    }

    private var rpcClientRouter: RpcClientRouter? = null

    @Autowired
    fun setRpcClientRouter(rpcClientRouter: RpcClientRouter) {
        this.rpcClientRouter = rpcClientRouter
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        GameContext.applicationContext = applicationContext
        GameContext.setSelf(this)
    }
}
