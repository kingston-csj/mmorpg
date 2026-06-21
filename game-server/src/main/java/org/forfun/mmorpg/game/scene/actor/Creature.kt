package org.forfun.mmmorpg.game.scene.actor

import jakarta.persistence.Transient
import org.forfun.mmorpg.game.attribute.AttributeContainer
import org.forfun.mmorpg.game.attribute.AttributeType
import org.forfun.mmorpg.game.buff.model.BuffContainer
import org.forfun.mmorpg.game.scene.actor.SceneActor
import org.forfun.mmorpg.game.scene.model.Life

/**
 * 生物体，就是会动的场景演员
 */
abstract class Creature : SceneActor() {

    @Transient
    var buffContainer: BuffContainer? = null

    @Transient
    var attrContainer = AttributeContainer()

    @Transient
    var life: Life? = null

    val hp: Long
        get() = life!!.currHp

    val attack: Int
        get() = attrContainer.getAttrValue(AttributeType.Attack).toInt()

    fun dispatchKey(): Int = mapId + lineId

    fun dispatchLine(): Int = lineId

    val isDie: Boolean
        get() = life!!.currHp <= 0
}
