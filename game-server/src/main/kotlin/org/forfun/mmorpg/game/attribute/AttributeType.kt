package org.forfun.mmorpg.game.attribute

enum class AttributeType(
    val id: Int,
    /**
     * 是否万分比
     */
    val percent: Boolean
) {

    Attack(1, false),
    Defense(1, false),
    Hp(1, false)
}
