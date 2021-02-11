package com.kingston.mmorpg.game.player.service;

import com.kingston.mmorpg.framework.net.socket.IdSession;
import com.kingston.mmorpg.game.account.AccountService;
import com.kingston.mmorpg.game.account.model.AccountProfile;
import com.kingston.mmorpg.game.base.MessagePusher;
import com.kingston.mmorpg.game.base.GameContext;
import com.kingston.mmorpg.game.database.user.entity.AccountEnt;
import com.kingston.mmorpg.game.player.message.ResAccountLogin;
import com.kingston.mmorpg.game.player.message.vo.PlayerLoginVo;
import com.kingston.mmorpg.game.player.model.PlayerProfile;
import com.kingston.mmorpg.game.scene.actor.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private PlayerService playerService;

	/**
	 *
	 * @param accountId 账号流水号
	 * @param password  账号密码
	 */
	public void handleAccountLogin(IdSession session, long accountId, String password) {
		AccountEnt account = accountService.getAccount(accountId);
		if (account == null) {
			account = new AccountEnt();
			account.setId(accountId);
			accountService.createNew(account);
		}
//		session.setAttribute(SessionProperties.ACCOUNT, accountId);

		List<PlayerLoginVo> players = new ArrayList<>();
		AccountProfile accountProfile = playerService.getAccountProfiles(accountId);
		List<PlayerProfile> playerProfiles = accountProfile.getPlayers();

		if (playerProfiles != null) {
			for (PlayerProfile playerProfile : playerProfiles) {
				PlayerLoginVo vo = new PlayerLoginVo();
				vo.setId(playerProfile.getPlayerId());
				vo.setName(playerProfile.getName());
				players.add(vo);
			}
		}

		ResAccountLogin loginMessage = new ResAccountLogin();
		loginMessage.setPlayers(players);
		MessagePusher.pushMessage(session, loginMessage);

	}

	/**
	 * 选角登录
	 * 
	 * @param session
	 * @param playerId
	 */
	public void handleSelectPlayer(IdSession session, long playerId) {
		Player player = playerService.getPlayer(playerId);
		player = playerService.getPlayer(playerId);
		if (player != null) {
			// 绑定session与玩家id
			session.setAttribute("playerId", playerId);
			// 加入在线列表
			GameContext.getSessionManager().registerSession(player, session);
		}
	}

}
