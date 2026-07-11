package org.forfun.mmorpg.game.scene.service

import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.config.domain.ConfigMonster
import org.forfun.mmorpg.game.database.config.domain.ConfigSceneSpawn
import org.forfun.mmorpg.game.scene.actor.Monster
import org.forfun.mmorpg.game.scene.model.Scene
import org.forfun.mmorpg.game.scene.model.SpawnMode

class SceneSpawnManager(private val scene: Scene) {

    private val spawnStatusMap = mutableMapOf<Int, SpawnStatus>()

    fun init() {
        val spawnConfigs = GameContext.getDataManager().queryByIndex(
            ConfigSceneSpawn::class.java,
            "mapId",
            scene.mapId
        )

        for (config in spawnConfigs) {
            val status = SpawnStatus(config)
            spawnStatusMap[config.id] = status

            when (config.spawnMode) {
                SpawnMode.ONCE -> {
                    spawnMonsters(config)
                }
                SpawnMode.TIME -> {
                    scheduleTimeSpawn(config)
                }
                SpawnMode.ROUND -> {
                }
            }
        }
    }

    private fun scheduleTimeSpawn(config: ConfigSceneSpawn) {
        GameContext.getSchedulerManager().scheduleAtFixedRate({
            spawnMonsters(config)
        }, 0, config.refreshInterval)
    }

    fun spawnRoundMonsters(round: Int) {
        val spawnConfigs = GameContext.getDataManager().queryByIndex(
            ConfigSceneSpawn::class.java,
            "mapId",
            scene.mapId
        )

        for (config in spawnConfigs) {
            if (config.spawnMode == SpawnMode.ROUND && config.refreshRound == round) {
                val status = spawnStatusMap[config.id]
                if (status != null && !status.hasSpawned) {
                    spawnMonsters(config)
                    status.hasSpawned = true
                }
            }
        }
    }

    private fun spawnMonsters(config: ConfigSceneSpawn) {
        val configMonster =
            GameContext.getDataManager().queryById(ConfigMonster::class.java, config.monsterId) ?: return

        val currentCount = getCurrentMonsterCount(config)

        if (config.maxCount in 1..currentCount) {
            return
        }

        val spawnNum = config.spawnNum
        for (i in 0 until spawnNum) {
            val monster = SceneSpawnUtil.createMonster(configMonster)
            monster.posX = SceneSpawnUtil.getRandomX(config)
            monster.posY = SceneSpawnUtil.getRandomY(config)
            monster.mapId = scene.mapId
            monster.lineId = scene.lineId
            monster.scene = scene

            scene.actorEnter(monster)
        }
    }

    private fun getCurrentMonsterCount(config: ConfigSceneSpawn): Int {
        return scene.getAliveMonsters().size
    }

    private class SpawnStatus(val config: ConfigSceneSpawn) {
        var lastSpawnTime: Long = 0
        var hasSpawned: Boolean = false
    }
}