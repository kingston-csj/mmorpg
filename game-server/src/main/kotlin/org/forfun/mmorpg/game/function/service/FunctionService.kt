package org.forfun.mmorpg.game.function.service

import jforgame.commons.util.NumberUtil
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.config.domain.ConfigFunction
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.function.event.PlayerFuncOpenEvent
import org.forfun.mmorpg.game.function.model.FunctionOpenType
import org.springframework.stereotype.Service

/**
 * 功能开启与关闭
 */
@Service
open class FunctionService {

    fun checkOpen(player: PlayerEnt, type: Int) {
        val targets = GameContext.dataManager().queryByIndex(
            ConfigFunction::class.java,
            "openType",
            FunctionOpenType.Level.type
        )
        targets?.filter { !player.isOpenedFunction(it.id) }
            ?.forEach { func ->
                if (player.level >= NumberUtil.intValue(func.openParams)) {
                    levelOpenFunc(player, func.id)
                }
            }
    }

    private fun levelOpenFunc(player: PlayerEnt, funcId: Int) {
        GameContext.playerService().savePlayer(player)
        GameContext.eventBus().post(PlayerFuncOpenEvent(player))
    }
}
