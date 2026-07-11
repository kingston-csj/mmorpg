package org.forfun.mmorpg.game.database.config.domain

import jforgame.data.annotation.Index
import org.forfun.mmorpg.game.scene.model.SpawnMode

class ConfigSceneSpawn {

    var id: Int = 0

    @Index
    /** 关联场景ID */
    var mapId: Int = 0

    /** 刷怪坐标 X */
    var posX: Int = 0

    /** 刷怪坐标 Y */
    var posY: Int = 0

    /** 单次刷新怪物数量 */
    var spawnNum: Int = 1

    /** 刷新半径（范围内随机坐标生成怪物，0为固定坐标） */
    var spawnRadius: Float = 0f

    /** 怪物ID */
    var monsterId: Int = 0

    /** 刷新模式 */
    var spawnMode: SpawnMode = SpawnMode.ONCE

    /** 时间刷新间隔（毫秒），TIME模式使用 */
    var refreshInterval: Long = 0

    /** 回合数，ROUND模式使用 */
    var refreshRound: Int = 1

    /** 该刷怪点最大怪物数量（0表示无限制） */
    var maxCount: Int = 0
}