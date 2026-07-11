package org.forfun.mmorpg.game.scene.service

import org.forfun.mmorpg.game.scene.director.DevilDungeonDirector
import org.forfun.mmorpg.game.scene.director.SceneDirector

enum class SceneCreateFactory(val type: Int, val director: Class<out SceneDirector>) {

    NormalScene(1, SceneDirector::class.java),

    DevilDungeon(2, DevilDungeonDirector::class.java)

}