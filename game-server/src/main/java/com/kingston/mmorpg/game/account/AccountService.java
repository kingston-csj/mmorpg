package com.kingston.mmorpg.game.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kingston.mmorpg.game.base.SpringContext;
import com.kingston.mmorpg.game.database.user.dao.AccountDao;
import com.kingston.mmorpg.game.database.user.entity.Account;

@Service
public class AccountService {
	
	@Autowired
	private AccountDao accountDao;
	
	@Cacheable(cacheNames = "account")
	public Account getAccount(long id) {
		Account account = accountDao.getOne(id);
		if (account != null) {
			SpringContext.getPlayerService().addAccountProfile(account);
		}
		return account;
	}
	
	@CachePut(cacheNames = "account")
	public Account createNew(Account account) {
		return account;
	}

}
