package org.forfun.mmorpg.game.attribute

class AttributeContainer {

    private val attrs = mutableMapOf<AttributeType, Long>()

    fun getAttrValue(type: AttributeType): Long {
        return attrs.getOrDefault(type, 0L)
    }
}
