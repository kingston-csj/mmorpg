package org.forfun.mmorpg.game.id;

public enum IdKey {

    /**
     * 玩家
     */
    PLAYER(1),

    /**
     * 道具
     */
    ITEM(2),

    /**
     * 战场房间号
     */
    MATCH(3, false),

    ;

    int id;

    /**
     * 是否需要持久化
     */
    boolean saveToDb;

    IdKey(int id) {
        this.id = id;
    }

    IdKey(int id, boolean saveToDb) {
        this.id = id;
        this.saveToDb = saveToDb;
    }

}