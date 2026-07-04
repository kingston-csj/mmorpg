package org.forfun.mmorpg.game.id

enum class IdKey(
    val id: Int,
    /**
     * 是否需要持久化
     */
    val saveToDb: Boolean = true
) {
    /**
     * 玩家
     */
    PLAYER(1),

    /**
     * 道具
     */
    ITEM(2),

    /**
     * 战场房间号
     */
    MATCH(3, false)
}
