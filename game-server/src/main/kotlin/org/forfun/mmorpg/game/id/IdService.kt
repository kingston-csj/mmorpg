package org.forfun.mmorpg.game.id

import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.user.entity.IdentityEnt
import java.util.concurrent.atomic.AtomicLong

class IdService {

    fun init() {
        for (idKey in IdKey.values()) {
            val identityEnt = getEntityFromCache(idKey)
            if (identityEnt.value == 0L) {
                identityEnt.value = getInitSeed()
            }
            identityEnt.factory = AtomicLong(identityEnt.value)
        }
    }

    private fun getInitSeed(): Long {
        //----------------id格式 -------------------------
        //----------long类型8个字节64个比特位----------------
        // 高16位          	| 低48位
        // serverId           自增长号
        val serverId = GameContext.serverConfig().serverId
        return serverId.toLong().shl(48)
    }

    fun getNextKey(key: IdKey): Long {
        // 获取缓存里的cache
        val identityEnt = getEntityFromCache(key)
        val factory = identityEnt.factory
            ?: throw IllegalStateException("IdentityEnt.factory未初始化，无法生成ID")
        val nextId = factory.incrementAndGet()
        if (key.saveToDb) {
            GameContext.asyncDbService().saveToDb(identityEnt)
        }
        return nextId
    }

    private fun getEntityFromCache(key: IdKey): IdentityEnt {
        return IdentityEnt()
    }

}