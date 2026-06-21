package org.forfun.mmorpg.game.account.model

import org.forfun.mmorpg.game.player.model.PlayerProfile
import java.util.ArrayList

class AccountProfile {

    var accountId: Long = 0

    var players: MutableList<PlayerProfile> = ArrayList()

    var recentPlayer: Long = 0

    fun addPlayerProfile(player: PlayerProfile) {
        this.players.add(player)
    }

    override fun toString(): String {
        return "AccountProfile [accountId=$accountId, players=$players, recentPlayer=$recentPlayer]"
    }
}
