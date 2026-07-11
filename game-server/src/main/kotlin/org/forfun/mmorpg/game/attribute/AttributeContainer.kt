package org.forfun.mmorpg.game.attribute

class AttributeContainer {

    private val attrs = mutableMapOf<AttributeType, Long>()

    fun getAttrValue(type: AttributeType): Long {
        return attrs.getOrDefault(type, 0L)
    }

    fun addAttr(type: AttributeType, value: Long) {
        attrs[type] = attrs.getOrDefault(type, 0L) + value
    }

    fun setAttr(type: AttributeType, value: Long) {
        attrs[type] = value
    }

    fun clear() {
        attrs.clear()
    }

    fun getAllAttrs(): Map<AttributeType, Long> {
        return attrs.toMap()
    }
}
