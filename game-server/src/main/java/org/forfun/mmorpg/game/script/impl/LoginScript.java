package org.forfun.mmorpg.game.script.impl;

import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.script.IScript;

public interface LoginScript extends IScript {

    void onLogin(PlayerEnt p);

    default String getId() {
        return "Login";
    }

}
