package org.forfun.mmorpg.game.database.config.domain

import jforgame.data.annotation.DataTable
import jforgame.data.annotation.Id

@DataTable(name = "configbuff")
class ConfigBuff {

    @Id
    var id: Int = 0

    var name: String? = null

    var duration: Int = 0

    var isOverlap: Boolean = false

    var overlapMax: Int = 3

    var effectType: String = ""

    var effectParams: String = ""

    var icon: String? = null
}