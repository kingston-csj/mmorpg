package org.forfun.mmorpg.game.database.config.domain

import jakarta.persistence.Id

/**
 * 地图资源
 */
class ConfigMap {

    @Id
    var id: Int = 0

    /**
     * 地图类型 0为普通场景，1为副本场景
     */
    var mapType: Byte = 0

    var name: String? = null
    var width: Int = 0
    var height: Int = 0

    /**
     * 基础分线数量
     */
    var coreLine: Int = 0

    /**
     * 最大分线数量
     */
    var maxLine: Int = 0
}
