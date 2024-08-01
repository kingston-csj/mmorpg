package org.forfun.mmorpg.game.database.user.dao;

import org.forfun.mmorpg.game.database.user.entity.AccountEnt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityDao extends JpaRepository<AccountEnt, Long> {

}
