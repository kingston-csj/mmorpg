package com.kingston.mmorpg.game.account;

import com.kingston.mmorpg.game.base.EntityCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kingston.mmorpg.game.base.SpringContext;
import com.kingston.mmorpg.game.database.user.dao.AccountDao;
import com.kingston.mmorpg.game.database.user.entity.AccountEnt;

@Service
public class AccountService implements EntityCacheService<AccountEnt, Long> {

	@Autowired
	private AccountDao accountDao;

	public AccountEnt getAccount(long id) {
		return getEntity(id,AccountEnt.class);
	}

	public AccountEnt createNew(AccountEnt account) {
		return putEntity(account);
	}

	@Override
	@Cacheable(cacheNames = "account")
	public AccountEnt getEntity(Long id, Class<AccountEnt> clazz) {
		AccountEnt account = accountDao.getOne(id);
		return account;
	}

	@Override
	@CachePut(cacheNames = "account")
	public AccountEnt putEntity(AccountEnt account) {
		SpringContext.getPlayerService().addAccountProfile(account);
		SpringContext.getAysncDbService().saveToDb(account);
		return account;
	}
}
