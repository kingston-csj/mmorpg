package com.kingston.mmorpg.game.base;

import com.kingston.mmorpg.game.database.user.BaseEntity;

import java.io.Serializable;

public interface EntityCacheService<E extends BaseEntity<PK>, PK extends Serializable & Comparable<PK>> {


    E getEntity(PK id, Class<E> clazz);

    BaseEntity<PK> putEntity(E entity);

    default BaseEntity<PK> removeEntityBy(PK id) {
        throw new UnsupportedOperationException();
    }
}
