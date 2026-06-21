package org.forfun.mmorpg.game.scene.factory

import org.forfun.mmorpg.game.database.config.domain.ConfigMonster
import org.forfun.mmorpg.game.scene.actor.Monster
import org.forfun.mmorpg.game.scene.actor.SceneActor

class MonsterCreator : ActorCreator {

    override fun create(prototype: ActorPrototype, vararg params: Any): SceneActor {
        val configMonster = prototype as ConfigMonster
        val monster = Monster()
        monster.modelId = configMonster.id
        monster.name = configMonster.name

        return monster
    }
}
