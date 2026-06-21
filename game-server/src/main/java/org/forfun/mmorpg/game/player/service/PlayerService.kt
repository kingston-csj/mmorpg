package org.forfun.mmorpg.game.player.service

import jforgame.commons.ds.ConcurrentHashSet
import jforgame.data.common.CommonConfig
import jforgame.data.common.CommonValueReloadListener
import jforgame.socket.share.IdSession
import org.forfun.mmorpg.framework.cache.BaseEntityCacheService
import org.forfun.mmorpg.framework.cache.EntityCacheAutowired
import org.forfun.mmorpg.game.account.model.AccountProfile
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.database.user.dao.PlayerDao
import org.forfun.mmorpg.game.database.user.entity.AccountEnt
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt
import org.forfun.mmorpg.game.logger.LoggerUtils
import org.forfun.mmorpg.game.player.message.ResPlayerLogin
import org.forfun.mmorpg.game.player.model.PlayerProfile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.HashSet
import java.util.concurrent.ConcurrentHashMap

@Service
class PlayerService : CommonValueReloadListener {

    companion object {
        const val CMD_REQ_ACCOUNT_LOGIN = 1
        const val CMD_REQ_CREATE_NEW = 2
        const val CMD_REQ_PLAYER_LOGIN = 3
        const val CMD_REQ_SELECT_PLAYER = 4
        const val CMD_RES_ACCOUNT_LOGIN = 51
        const val CMD_RES_LOGIN = 52
    }

    @CommonConfig("playerMaxLevel")
    var maxValue: Int = 0

    /**
     * 在线玩家列表
     */
    val onlines: MutableSet<Long> = ConcurrentHashSet()

    /**
     * 全服所有角色的简况
     */
    private val playerProfiles: ConcurrentHashMap<Long, PlayerProfile> = ConcurrentHashMap()

    /**
     * 全服所有账号的简况
     */
    private val accountProfiles: ConcurrentHashMap<Long, AccountProfile> = ConcurrentHashMap()

    @Autowired
    private lateinit var playerDao: PlayerDao

    @EntityCacheAutowired
    private lateinit var playerCacheService: BaseEntityCacheService<PlayerEnt, Long>

    fun loadAllPlayerProfiles() {
        val allPlayers = playerDao.queryAllPlayers()
        allPlayers.forEach { player ->
            playerProfiles.put(player.playerId, player)
        }
        LoggerUtils.error("加载玩家基本数据，总量为{}", allPlayers.size.toLong())
    }

    fun getPlayer(id: Long): PlayerEnt {
        return playerCacheService.getOrCreate(id) {
            val player = PlayerEnt()
            player.id = id
            player
        }
    }

    fun savePlayer(player: PlayerEnt) {
        playerCacheService.putEntity(player)
    }

    fun login(session: IdSession, playerId: Long): ResPlayerLogin {
        val player = PlayerEnt()
        return ResPlayerLogin()
    }

    fun getOnlinePlayers(): Set<Long> {
        return HashSet(this.onlines)
    }

    private fun addPlayerProfile(baseInfo: PlayerProfile) {
        playerProfiles.put(baseInfo.playerId, baseInfo)

        val accountId = baseInfo.accountId
        val account = GameContext.getAccountService()!!.getEntity(accountId)
        accountProfiles.putIfAbsent(accountId, AccountProfile())
        val accountProfile = accountProfiles[accountId]
        accountProfile!!.addPlayerProfile(baseInfo)
    }

    fun getAccountProfiles(accountId: Long): AccountProfile? {
        var accountProfile = accountProfiles[accountId]
        if (accountProfile != null) {
            return accountProfile
        }
        val account = GameContext.getAccountService()!!.getEntity(accountId)
        if (account != null) {
            accountProfile = AccountProfile()
            accountProfile!!.accountId = accountId
            accountProfiles.putIfAbsent(accountId, accountProfile)
        }
        return accountProfile
    }

    fun addAccountProfile(account: AccountEnt) {
        val accountId = account.id
        if (accountProfiles.containsKey(accountId)) {
            throw RuntimeException("账号重复-->" + accountId)
        }
        val accountProfile = AccountProfile()
        accountProfile.accountId = accountId
        accountProfiles.put(accountId, accountProfile)
    }

    fun addExp(player: PlayerEnt, exp: Long) {
        // TODO implement
    }

    override fun afterReload() {
        // TODO implement
    }
}
