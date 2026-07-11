package org.forfun.mmorpg.game.database.config.domain

import org.forfun.mmorpg.game.scene.factory.ActorPrototype

class ConfigMonster : ActorPrototype {

    var id: Int = 0
    var name: String? = null

    var attack:Int =0

    var hp:Int =0

    /** 移动速度 */
    var speed: Int = 0

    /**
     * 技能列表，以_隔开
     */
    var skills: String = ""

    /**
     * 怪物复活毫秒数，为0代表不复活
     */
    var reviveMillis:Int =0
}
