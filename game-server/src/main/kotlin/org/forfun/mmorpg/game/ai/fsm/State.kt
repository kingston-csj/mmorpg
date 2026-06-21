package org.forfun.mmorpg.game.ai.fsm

import org.forfun.mmmorpg.game.scene.actor.Creature


interface State {

    /**
     * 切换至新状态
     * @param creature
     */
    fun onEnter(creature: Creature)

    /**
     * 离开当前状态
     * @param creature
     */
    fun onExit(creature: Creature)

    /**
     * 每一个tick跑的业务
     * @param creature
     */
    fun execute(creature: Creature)
}
