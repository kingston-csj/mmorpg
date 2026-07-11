package org.forfun.mmorpg.game.scene.director

import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.scene.actor.ActorType
import org.forfun.mmorpg.game.scene.actor.Monster
import org.forfun.mmorpg.game.scene.actor.SceneActor
import org.forfun.mmorpg.game.scene.model.Scene

open class SceneDirector(var scene: Scene ) {

    fun onActorEnter(actor: SceneActor) {
        if (actor.type == ActorType.Player) {
            onPlayerEnter(actor as PlayerEnt)
        } else if (actor.type == ActorType.Monster) {
            onMonsterEnter(actor as Monster)
        }
    }

    fun onActorLeave(actor: SceneActor) {
        if (actor.type == ActorType.Player) {
            onPlayerLeave(actor as PlayerEnt)
        } else if (actor.type == ActorType.Monster) {
            onMonsterLeave(actor as Monster)
        }
    }

    fun onActorDead(actor: SceneActor) {
        if (actor.type == ActorType.Player) {
            onPlayerDead(actor as PlayerEnt)
        } else if (actor.type == ActorType.Monster) {
            onMonsterDead(actor as Monster)
        }
    }

    protected open fun onPlayerEnter(player: PlayerEnt) {}

    protected open fun onMonsterEnter(monster: Monster) {}

    protected open fun onPlayerLeave(player: PlayerEnt) {}

    protected open fun onMonsterLeave(monster: Monster) {}

    protected open fun onPlayerDead(player: PlayerEnt) {}

    protected open fun onMonsterDead(monster: Monster) {}
}
