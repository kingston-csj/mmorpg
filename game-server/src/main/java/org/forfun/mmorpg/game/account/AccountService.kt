package org.forfun.mmorpg.game.account;

import org.forfun.mmorpg.framework.cache.BaseEntityCacheService;
import org.forfun.mmorpg.framework.cache.EntityCacheAutowired;
import org.forfun.mmorpg.game.base.EntityCacheService;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.user.dao.AccountDao;
import org.forfun.mmorpg.game.database.user.entity.AccountEnt;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service;

@Service
class AccountService {


    @EntityCacheAutowired
    private lateinit var accountCacheService: BaseEntityCacheService<AccountEnt, Long>

    fun createNew(account: AccountEnt): AccountEnt {
        return accountCacheService.getOrCreate(account.id) {
            var account = AccountEnt()
            account
        };
    }

    fun getEntity(id:Long):AccountEnt {
        return accountCacheService.getEntity(id)
    }

}
