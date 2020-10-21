package com.kingston.mmorpg.game.base;

import com.kingston.mmorpg.game.database.user.BaseEntity;

import java.io.Serializable;

public interface EntityCacheService {


    <E extends BaseEntity<PK>,PK extends Serializable & Comparable<PK>> E getEntityBy(PK id, Class<E> clazz);

    <PK> BaseEntity putEntityBy(PK id);

    default <PK> BaseEntity removeEntityBy(PK id) {
        throw new UnsupportedOperationException();
    }
}
