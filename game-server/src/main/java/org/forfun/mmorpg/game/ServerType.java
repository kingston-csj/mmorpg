package org.forfun.mmorpg.game;

public enum ServerType {

    GATE(0, "网关服"),

    GAME(1, "游戏服"),

    FIGHT(2, "战斗服"),

    ;

    ServerType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    int type;

    String name;

    public static ServerType of(int type) {
        for (ServerType value : ServerType.values()) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }
}
