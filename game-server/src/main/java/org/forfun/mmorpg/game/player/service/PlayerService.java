package org.forfun.mmorpg.game.player.service;

import jforgame.commons.ds.ConcurrentHashSet;
import jforgame.socket.share.IdSession;
import lombok.extern.java.Log;
import org.forfun.mmorpg.game.account.model.AccountProfile;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.database.config.inject.CommonValueInject;
import org.forfun.mmorpg.game.database.config.inject.CommonValueReloadListener;
import org.forfun.mmorpg.game.database.config.inject.IntArrayConfigValueParser;
import org.forfun.mmorpg.game.database.user.dao.PlayerDao;
import org.forfun.mmorpg.game.database.user.entity.AccountEnt;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.forfun.mmorpg.game.player.message.ResPlayerLogin;
import org.forfun.mmorpg.game.player.model.PlayerProfile;
import org.forfun.mmorpg.game.script.impl.LoginScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Log
public class PlayerService implements CommonValueReloadListener {

	public static final byte CMD_REQ_ACCOUNT_LOGIN = 1;

	public static final byte CMD_REQ_CREATE_NEW = 2;

	public static final byte CMD_REQ_PLAYER_LOGIN = 3;

	public static final byte CMD_REQ_SELECT_PLAYER = 4;

	public static final byte CMD_RES_ACCOUNT_LOGIN = 51;


	public static final byte CMD_RES_LOGIN = 52;

	@CommonValueInject(alias = "playerMaxLevel")
	private int maxValue;

	@CommonValueInject(alias = "specialLevels", parser = IntArrayConfigValueParser.class)
	private int[] specialLevels;

	/**
	 * 在线玩家列表
	 */
	private Set<Long> onlines = new ConcurrentHashSet<>();

	/** 全服所有角色的简况 */
	private ConcurrentMap<Long, PlayerProfile> playerProfiles = new ConcurrentHashMap<>();

	/** 全服所有账号的简况 */
	private ConcurrentMap<Long, AccountProfile> accountProfiles = new ConcurrentHashMap<>();

	@Autowired
	private PlayerDao playerDao;

	@Autowired
	private PlayerCacheService playerCacheService;

	public void loadAllPlayerProfiles() {
		List<PlayerProfile> allPlayers = playerDao.queryAllPlayers();
		allPlayers.forEach(player -> {
			playerProfiles.put(player.getPlayerId(), player);
		});
		LoggerUtils.error("加载玩家基本数据，总量为{}", allPlayers.size());
	}

	public PlayerEnt getPlayer(long id) {
		return playerCacheService.getEntity(id);
	}

	/**
	 * 保存玩家数据
	 * 
	 * @param player
	 */
	public void savePlayer(PlayerEnt player) {
		playerCacheService.putEntity(player);
	}

	public ResPlayerLogin login(IdSession session, long playerId) {
		PlayerEnt player = new PlayerEnt();
		GameContext.getScriptService().getScript(LoginScript.class).onLogin(player);
//		session.bindDispatcher(player);
		return new ResPlayerLogin();
	}

	public Set<Long> getOnlinePlayers() {
		return new HashSet<>(this.onlines);
	}

	private void addPlayerProfile(PlayerProfile baseInfo) {
		playerProfiles.put(baseInfo.getPlayerId(), baseInfo);

		long accountId = baseInfo.getAccountId();
		// 必须将account加载并缓存
		AccountEnt account = GameContext.getAccountService().getEntity(accountId);
		accountProfiles.putIfAbsent(accountId, new AccountProfile());
		AccountProfile accountProfile = accountProfiles.get(accountId);
		accountProfile.addPlayerProfile(baseInfo);
	}

	public AccountProfile getAccountProfiles(long accountId) {
		AccountProfile accountProfile = accountProfiles.get(accountId);
		if (accountProfile != null) {
			return accountProfile;
		}
		AccountEnt account = GameContext.getAccountService().getEntity(accountId);
		if (account != null) {
			accountProfile = new AccountProfile();
			accountProfile.setAccountId(accountId);
			accountProfiles.putIfAbsent(accountId, accountProfile);
		}
		return accountProfile;
	}

	public void addAccountProfile(AccountEnt account) {
		long accountId = account.getId();
		if (accountProfiles.containsKey(accountId)) {
			throw new RuntimeException("账号重复-->" + accountId);
		}
		AccountProfile accountProfile = new AccountProfile();
		accountProfile.setAccountId(accountId);
		accountProfiles.put(accountId, accountProfile);
	}

	public void addExp(PlayerEnt player, long exp) {

	}

	@Override
	public void afterReload() {
	}
}
