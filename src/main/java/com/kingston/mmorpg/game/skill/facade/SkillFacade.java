package com.kingston.mmorpg.game.skill.facade;

import org.springframework.stereotype.Component;

import com.kingston.mmorpg.framework.eventbus.Subscribe;
import com.kingston.mmorpg.game.player.event.PlayerLoginEvent;
import com.kingston.mmorpg.game.scene.actor.Player;

@Component
public class SkillFacade {
	
	@Subscribe
	public void onPlayerLogin(PlayerLoginEvent loginEvent) {
		Player player = loginEvent.getOwner();
		System.err.println("角色" + player.getId() + "登录，准备下发技能列表");
	}

}
