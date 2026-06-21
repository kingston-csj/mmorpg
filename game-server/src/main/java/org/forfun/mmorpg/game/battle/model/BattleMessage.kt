package org.forfun.mmorpg.game.battle.model

import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt

interface BattleMessage : Message {

    fun getBattleServerId(player: PlayerEnt): Int {
//        if (battleContext == null) {
//        BattleContext battleContext = player.getBattleContext();
//            return 0;
//        }
//        return battleContext.getFightSid();
        return 0
    }

    fun getPlayerId(): Long
}
