package org.forfun.mmorpg.game.database.user;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface BaseEntity<ID extends Serializable & Comparable<ID>> {

    ID getId();

    /**
     * springdata jpa没有类似MongoTemplate的工具，只能手动绑定Entity与CrudRepository
     *
     * @return
     */
    CrudRepository<? extends BaseEntity<ID>, ID>  getCrudRepository();

    default String getKey() {
        return getClass().getSimpleName() + "@" + getId().toString();
    }

}
