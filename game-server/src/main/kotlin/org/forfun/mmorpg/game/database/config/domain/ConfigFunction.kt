package org.forfun.mmorpg.game.database.config.domain

import jforgame.data.annotation.DataTable
import jforgame.data.annotation.Id
import org.apache.commons.lang3.math.NumberUtils
import org.forfun.mmorpg.game.function.model.FunctionOpenType

@DataTable(name = "configfunction")
class ConfigFunction {

    @Id
    var id: Int = 0

    /**
     * 开启类型 {@link FunctionOpenType#getType()}
     */
    var openType: FunctionOpenType? = null

    /**
     * 开启类型对应的参数
     */
    var openParams: String? = null

    fun getOpenMainParam(): Int {
        return NumberUtils.toInt(openParams)
    }
}
