package org.forfun.mmorpg.game;

public interface ServerLayer {

    void init();

    default void onCenterServerConnected() {

    }
}
