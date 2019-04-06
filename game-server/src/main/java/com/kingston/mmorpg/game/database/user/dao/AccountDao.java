package com.kingston.mmorpg.game.database.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingston.mmorpg.game.database.user.entity.AccountEnt;

public interface AccountDao extends JpaRepository<AccountEnt, Long> {

}
