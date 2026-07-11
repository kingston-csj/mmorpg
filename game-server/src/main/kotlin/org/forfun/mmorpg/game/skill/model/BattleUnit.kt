package org.forfun.mmorpg.game.skill.model

import org.forfun.mmorpg.game.scene.actor.Creature

class BattleUnit {

    /**
     * 施法者
     */
    lateinit var effect : Creature
    /**
     * 受击者
     */
    lateinit var target : Creature

}