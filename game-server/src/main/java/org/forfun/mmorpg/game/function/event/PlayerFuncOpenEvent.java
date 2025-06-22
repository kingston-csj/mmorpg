package org.forfun.mmorpg.game.function.event;

import jforgame.commons.eventbus.BaseEvent;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;

public class PlayerFuncOpenEvent implements BaseEvent {

    private PlayerEnt player;

    public PlayerFuncOpenEvent(PlayerEnt player) {
        this.player = player;
    }

    @Override
    public PlayerEnt getOwner() {
        return player;
    }

}
