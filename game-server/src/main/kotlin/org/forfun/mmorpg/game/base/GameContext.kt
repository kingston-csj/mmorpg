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
        private lateinit var applicationContext: ApplicationContext

        @JvmStatic
        fun setSelf(context: GameContext) {
            self = context
        }

        // 容器上下文对外获取
        @JvmStatic
        fun context(): ApplicationContext = applicationContext

        // 业务服务获取
        @JvmStatic
        fun serverConfig() = self.serverConfig
        @JvmStatic
        fun eventBus(): EventBus = self.eventBus
        @JvmStatic
        fun asyncDbService(): AsyncDbService = self.asyncDbService
        @JvmStatic
        fun sessionManager(): SessionManager = self.sessionManager
        @JvmStatic
        fun dataManager(): DataManager = self.dataManager
        @JvmStatic
        fun playerService(): PlayerService = self.playerService
        @JvmStatic
        fun accountService(): AccountService = self.accountService
        @JvmStatic
        fun schedulerManager(): SchedulerManager = self.schedulerManager
        @JvmStatic
        fun buffService(): BuffService = self.buffService
        @JvmStatic
        fun messageFactory(): MessageFactory = self.messageFactory
        @JvmStatic
        fun rpcClientRouter(): RpcClientRouter = self.rpcClientRouter

        // Bean工具
        @JvmStatic
        fun <T : Any> getBean(clazz: Class<T>): T {
            return try {
                applicationContext.getBean(clazz)
            } catch (e: Exception) {
                throw IllegalStateException("缺失Bean: ${clazz.name}", e)
            }
        }

        @JvmStatic
        fun <T : Any> getBeansOfType(clazz: Class<T>): Collection<T> {
            return applicationContext.getBeansOfType(clazz).values
        }

        @JvmStatic
        fun <T : Any> getBean(name: String, requiredType: Class<T>): T {
            return try {
                applicationContext.getBean(name, requiredType)
            } catch (e: Exception) {
                throw IllegalStateException("缺失Bean name=$name type=${requiredType.name}", e)
            }
        }

        @JvmStatic
        fun getBeansWithAnnotation(annotationType: Class<out Annotation>): Map<String, Any> {
            return applicationContext.getBeansWithAnnotation(annotationType)
        }
    }

    // 全部延迟初始化引用类型
    private lateinit var serverConfig: ServerConfig
    private lateinit var eventBus: EventBus
    private lateinit var asyncDbService: AsyncDbService
    private lateinit var sessionManager: SessionManager
    private lateinit var dataManager: DataManager
    private lateinit var playerService: PlayerService
    private lateinit var accountService: AccountService
    private lateinit var schedulerManager: SchedulerManager
    private lateinit var buffService: BuffService
    private lateinit var messageFactory: MessageFactory
    private lateinit var rpcClientRouter: RpcClientRouter

    @Autowired
    fun setServerConfig(serverConfig: ServerConfig) {
        this.serverConfig = serverConfig
    }

    @Autowired
    fun setEventBus(eventBus: EventBus) {
        this.eventBus = eventBus
    }

    @Autowired
    fun setAsyncDbService(asyncDbService: AsyncDbService) {
        this.asyncDbService = asyncDbService
    }

    @Autowired
    fun setSessionManager(sessionManager: SessionManager) {
        this.sessionManager = sessionManager
    }

    @Autowired
    fun setDataManager(dataManager: DataManager) {
        this.dataManager = dataManager
    }

    @Autowired
    fun setPlayerService(playerService: PlayerService) {
        this.playerService = playerService
    }

    @Autowired
    fun setAccountService(accountService: AccountService) {
        this.accountService = accountService
    }

    @Autowired
    fun setSchedulerManager(schedulerManager: SchedulerManager) {
        this.schedulerManager = schedulerManager
    }

    @Autowired
    fun setBuffService(buffService: BuffService) {
        this.buffService = buffService
    }

    @Autowired
    fun setMessageFactory(messageFactory: MessageFactory) {
        this.messageFactory = messageFactory
    }

    @Autowired
    fun setRpcClientRouter(rpcClientRouter: RpcClientRouter) {
        this.rpcClientRouter = rpcClientRouter
    }

    override fun setApplicationContext(ctx: ApplicationContext) {
        // 给companion的lateinit上下文赋值
        applicationContext = ctx
        setSelf(this)
    }
}