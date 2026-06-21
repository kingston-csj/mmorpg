package org.forfun.mmorpg.game

enum class ServerType(val type: Int, val desc: String) {

    GATE(0, "网关服"),

    GAME(1, "游戏服"),

    FIGHT(2, "战斗服"),

    CENTRE(10, "中心服");

    companion object {
        fun of(type: Int): ServerType? {
            return entries.find { it.type == type }
        }
    }
}
