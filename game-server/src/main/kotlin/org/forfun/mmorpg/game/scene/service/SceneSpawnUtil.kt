package org.forfun.mmorpg.game.scene.service

import org.forfun.mmorpg.game.database.config.domain.ConfigMonster
import org.forfun.mmorpg.game.database.config.domain.ConfigSceneSpawn
import org.forfun.mmorpg.game.scene.actor.Monster
import org.forfun.mmorpg.game.scene.factory.MonsterCreator
import java.util.*

object SceneSpawnUtil {

    private val random = Random()

    fun createMonster(config: ConfigMonster): Monster {
        val creator = MonsterCreator()
        return creator.create(config) as Monster
    }

    fun getRandomX(config: ConfigSceneSpawn): Double {
        return if (config.spawnRadius > 0) {
            val offset = (random.nextDouble() - 0.5) * 2 * config.spawnRadius
            config.posX + offset
        } else {
            config.posX.toDouble()
        }
    }

    fun getRandomY(config: ConfigSceneSpawn): Double {
        return if (config.spawnRadius > 0) {
            val offset = (random.nextDouble() - 0.5) * 2 * config.spawnRadius
            config.posY + offset
        } else {
            config.posY.toDouble()
        }
    }
}