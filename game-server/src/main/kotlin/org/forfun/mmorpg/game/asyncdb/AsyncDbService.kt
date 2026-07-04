package org.forfun.mmorpg.game.asyncdb

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import jforgame.commons.persist.*
import org.forfun.mmorpg.game.ServerType
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.springframework.stereotype.Service

/**
 * 异步持久化服务
 */
@Service
class AsyncDbService : DbService {
    private var playerWorker: PersistContainer? = null

    private var commonWorker: PersistContainer? = null

    @PostConstruct
    private fun init() {
        val savingStrategy: SavingStrategy = EntitySavingStrategy()
        playerWorker = QueueContainer("playerDbContainer", savingStrategy)
        commonWorker = QueueContainer("commonDbContainer", savingStrategy)
    }


    override fun saveToDb(entity: Entity<*>?) {
        if (GameContext.serverType != ServerType.GAME) {
            // only game server can saving data
            return
        }
        if (entity is PlayerEnt) {
            playerWorker!!.receive(entity)
        } else {
            commonWorker!!.receive(entity)
        }
    }

    override fun deleteFromDb(entity: Entity<*>?) {
    }

    @PreDestroy
    override fun shutDown() {
        playerWorker!!.shutdownGraceful()
        commonWorker!!.shutdownGraceful()
    }
}