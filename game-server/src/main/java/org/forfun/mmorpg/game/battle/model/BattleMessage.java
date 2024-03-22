package org.forfun.mmorpg.game.battle.model;

import jforgame.socket.share.message.Message;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;

public interface BattleMessage extends Message {

    default int getBattleServerId(PlayerEnt player) {
        BattleContext battleContext = player.getBattleContext();
        if (battleContext == null) {
            return 0;
        }
        return battleContext.getFightSid();
    }

    long getPlayerId();
}
