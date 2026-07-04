package org.forfun.mmorpg.game.player.model

data class PlayerProfile(
    var playerId: Long = 0,
    var accountId: Long = 0,
    var name: String? = null,
    var level: Int = 0
) {
    override fun toString(): String {
        return "PlayerBaseInfo [id=$playerId, accountId=$accountId, name=$name, level=$level]"
    }
}
