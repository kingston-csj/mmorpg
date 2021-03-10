package org.forfun.mmorpg.game.database.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.forfun.mmorpg.game.database.user.entity.AccountEnt;

public interface AccountDao extends JpaRepository<AccountEnt, Long> {

}
