package org.forfun.mmorpg.game.base;

import org.forfun.mmorpg.game.database.user.BaseEntity;

import java.io.Serializable;

public interface EntityCacheService<E extends BaseEntity<PK>, PK extends Serializable & Comparable<PK>> {


    E getEntity(PK id);

    BaseEntity<PK> putEntity(E entity);

    default BaseEntity<PK> removeEntity(PK id) {
        throw new UnsupportedOperationException();
    }
}
