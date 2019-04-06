package com.kingston.mmorpg.game.database.user;

import org.springframework.data.repository.CrudRepository;

public interface BaseEntity {

	CrudRepository getCrudRepository();

}
