package com.kingston.mmorpg.game.script.impl;

import com.kingston.mmorpg.game.database.user.entity.PlayerEnt;
import com.kingston.mmorpg.game.script.IScript;

public interface LoginScript extends IScript {

    void onLogin(PlayerEnt p);

    default String getId() {
        return "Login";
    }

}
