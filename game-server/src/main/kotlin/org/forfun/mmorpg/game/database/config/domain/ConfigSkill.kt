package org.forfun.mmorpg.game.database.config.domain

import jforgame.data.annotation.DataTable
import jforgame.data.annotation.Id

@DataTable(name = "configskill")
class ConfigSkill {

    @Id
    var id: Int = 0
    var name: String? = null
    var needLevel: Int = 0

    /**
     * 技能效果说明
     */
    var effect: String? = null
}
