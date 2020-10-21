package com.kingston.mmorpg.game.database.user;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

public interface BaseEntity<PK extends Serializable & Comparable<PK>> {

	<PK> PK getId();

	CrudRepository getCrudRepository();

}
