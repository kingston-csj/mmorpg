package org.forfun.mmorpg.game.ai.fsm

import org.forfun.mmmorpg.game.scene.actor.Creature


abstract class Transition(private val from: State, private val to: State) {

    /**
     * 条件判定
     * @param creature
     * @return
     */
    abstract fun meetCondition(creature: Creature): Boolean

    fun fromState(): State = this.from

    fun toState(): State = this.to
}
