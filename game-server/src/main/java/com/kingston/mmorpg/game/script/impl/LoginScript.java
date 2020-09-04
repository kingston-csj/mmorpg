package com.kingston.mmorpg.game.script.impl;

import com.kingston.mmorpg.game.scene.actor.Player;
import com.kingston.mmorpg.game.script.IScript;

public interface LoginScript extends IScript {

    void onLogin(Player p);

    default String getId() {
        return "Login";
    }

}
