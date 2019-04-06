package com.kingston.mmorpg.game.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kingston.mmorpg.game.base.SpringContext;
import com.kingston.mmorpg.game.database.user.dao.AccountDao;
import com.kingston.mmorpg.game.database.user.entity.AccountEnt;

@Service
public class AccountService {

	@Autowired
	private AccountDao accountDao;

	@Cacheable(cacheNames = "account")
	public AccountEnt getAccount(long id) {
		AccountEnt account = accountDao.getOne(id);
		return account;
	}

	@CachePut(cacheNames = "account")
	public AccountEnt createNew(AccountEnt account) {
		SpringContext.getPlayerService().addAccountProfile(account);
		SpringContext.getAysncDbService().add2Queue(account);
		return account;
	}

}
