package org.forfun.mmmorpg.game.scene.actor

import jakarta.persistence.Transient
import org.forfun.mmorpg.game.attribute.AttributeContainer
import org.forfun.mmorpg.game.attribute.AttributeType
import org.forfun.mmorpg.game.buff.model.BuffContainer
import org.forfun.mmorpg.game.scene.actor.SceneActor
import org.forfun.mmorpg.game.scene.model.AoiObserver
import org.forfun.mmorpg.game.scene.model.Life

/**
 * 生物体，就是会动的场景演员
 */
abstract class Creature : SceneActor() {

    @Transient
    lateinit var buffContainer: BuffContainer

    @Transient
    var attrContainer = AttributeContainer()

    @Transient
    lateinit var life: Life

    @Transient
    val aoiObserver = AoiObserver()

    val hp: Long
        get() = life.currHp

    val attack: Int
        get() = attrContainer.getAttrValue(AttributeType.Attack).toInt()

    fun dispatchKey(): Int = mapId * 100 + lineId

    val isDie: Boolean
        get() = life.currHp <= 0

}
