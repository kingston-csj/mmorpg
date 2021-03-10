package org.forfun.mmorpg.game.database.user;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface BaseEntity<PK extends Serializable & Comparable<PK>> {

	<PK> PK getId();

	CrudRepository getCrudRepository();

	default String getKey() {
		return getClass().getSimpleName() + "@" + getId().toString();
	}

}
