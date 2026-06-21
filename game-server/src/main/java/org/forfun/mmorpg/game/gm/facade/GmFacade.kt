package org.forfun.mmorpg.game.gm.facade

import jforgame.socket.share.IdSession
import jforgame.socket.share.annotation.MessageRoute
import jforgame.socket.share.annotation.RequestHandler
import org.forfun.mmorpg.game.Modules
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.gm.GmDispatcher
import org.forfun.mmorpg.game.gm.message.ReqGmCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
@MessageRoute(module = Modules.GM)
open class GmFacade {

    @Autowired
    private lateinit var gmDispatcher: GmDispatcher

    @RequestHandler
    fun reqGmExec(session: IdSession, req: ReqGmCommand) {
        val player = session.getAttribute("PLAYER") as PlayerEnt
        val params = req.params!!.split("\\s+".toRegex()).toTypedArray()
        gmDispatcher.dispatch(player, params)
    }
}
