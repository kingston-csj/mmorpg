package com.kingston.mmorpg.game.database.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingston.mmorpg.game.database.user.entity.Account;

public interface AccountDao extends JpaRepository<Account, Long> {

}
