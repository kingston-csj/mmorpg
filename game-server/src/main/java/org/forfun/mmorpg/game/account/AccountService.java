package org.forfun.mmorpg.game.account;

import org.forfun.mmorpg.game.base.EntityCacheService;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.dao.AccountDao;
import org.forfun.mmorpg.game.database.user.entity.AccountEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements EntityCacheService<AccountEnt, Long> {

	@Autowired
	private AccountDao accountDao;

	public AccountEnt createNew(AccountEnt account) {
		return putEntity(account);
	}

	@Override
	@Cacheable(cacheNames = "account")
	public AccountEnt getEntity(Long id) {
		return accountDao.findById(id).orElse(null);
	}

	@Override
	@CachePut(cacheNames = "account")
	public AccountEnt putEntity(AccountEnt account) {
		GameContext.getPlayerService().addAccountProfile(account);
		GameContext.getAysncDbService().saveToDb(account);
		return account;
	}
}
