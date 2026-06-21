package org.forfun.mmorpg.game.account

import org.forfun.mmorpg.game.database.user.entity.AccountEnt;
import org.forfun.mmorpg.framework.cache.BaseEntityCacheService
import org.forfun.mmorpg.framework.cache.EntityCacheAutowired
import org.springframework.stereotype.Service;
import kotlin.jvm.optionals.getOrNull

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

    fun getEntity(id:Long):AccountEnt? {
        return accountCacheService.getEntity(id).getOrNull()
    }

}
