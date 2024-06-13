package org.forfun.mmorpg.game.player.service;

import jforgame.socket.share.IdSession;
import org.forfun.mmorpg.game.account.AccountService;
import org.forfun.mmorpg.game.account.model.AccountProfile;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.base.MessageUtil;
import org.forfun.mmorpg.game.database.user.entity.AccountEnt;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.player.message.ResAccountLogin;
import org.forfun.mmorpg.game.player.message.vo.PlayerLoginVo;
import org.forfun.mmorpg.game.player.model.PlayerProfile;
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
		AccountEnt account = accountService.getEntity(accountId);
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
		MessageUtil.pushMessage(session, loginMessage);

	}

	/**
	 * 选角登录
	 * 
	 * @param session
	 * @param playerId
	 */
	public void handleSelectPlayer(IdSession session, long playerId) {
		PlayerEnt player = playerService.getPlayer(playerId);
		player = playerService.getPlayer(playerId);
		if (player != null) {
			// 绑定session与玩家id
			session.setAttribute("playerId", playerId);
			// 加入在线列表
			GameContext.getSessionManager().registerNewPlayer(player, session);
		}
	}

}
