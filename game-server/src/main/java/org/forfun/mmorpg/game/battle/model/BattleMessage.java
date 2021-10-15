package org.forfun.mmorpg.game.battle.model;

import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.protocol.Message;

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
