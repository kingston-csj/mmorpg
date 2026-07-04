package org.forfun.mmorpg.game.function.facade

import jforgame.commons.eventbus.Subscribe
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.function.model.FunctionOpenType
import org.forfun.mmorpg.game.function.service.FunctionService
import org.forfun.mmorpg.game.player.event.PlayerLevelUpEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
open class FunctionFacade {

    @Autowired
    private lateinit var funcService: FunctionService

    @Subscribe
    fun onPlayerLevelUp(levelUpEvent: PlayerLevelUpEvent) {
        val player = levelUpEvent.owner
        funcService.checkOpen(player, FunctionOpenType.Level.type)
    }
}
