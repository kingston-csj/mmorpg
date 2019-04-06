package com.kingston.mmorpg.game.login.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingston.mmorpg.framework.net.socket.IoSession;
import com.kingston.mmorpg.framework.net.socket.MessagePusher;
import com.kingston.mmorpg.game.account.AccountService;
import com.kingston.mmorpg.game.account.model.AccountProfile;
import com.kingston.mmorpg.game.base.SpringContext;
import com.kingston.mmorpg.game.database.user.entity.AccountEnt;
import com.kingston.mmorpg.game.login.message.vo.PlayerLoginVo;
import com.kingston.mmorpg.game.login.message.vo.ResAccountLogin;
import com.kingston.mmorpg.game.player.model.PlayerProfile;
import com.kingston.mmorpg.game.player.service.PlayerService;
import com.kingston.mmorpg.game.scene.actor.Player;

@Service
public class LoginService {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PlayerService playerService;
	
	/**
	 *
	 * @param accoundId 账号流水号
	 * @param password  账号密码
	 */
	public void handleAccountLogin(IoSession session, long accountId, String password) {
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
	 * @param session
	 * @param playerId
	 */
	public void handleSelectPlayer(IoSession session, long playerId) {
		Player player = playerService.getPlayer(playerId);
		if (player != null) {
			//绑定session与玩家id
			session.updateAttr("plaeyrId", playerId);
			//加入在线列表
			SpringContext.getSessionManager().registerSession(player, session);
		}
	}

}
