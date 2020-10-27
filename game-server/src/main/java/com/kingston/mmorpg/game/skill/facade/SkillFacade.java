package com.kingston.mmorpg.game.skill.facade;

import com.kingston.mmorpg.framework.eventbus.Subscribe;
import com.kingston.mmorpg.framework.net.socket.annotation.ModuleMeta;
import com.kingston.mmorpg.game.Modules;
import com.kingston.mmorpg.game.base.MessagePusher;
import com.kingston.mmorpg.game.database.config.container.ConfigSkillContainer;
import com.kingston.mmorpg.game.database.config.domain.ConfigSkill;
import com.kingston.mmorpg.game.player.event.PlayerLevelUpEvent;
import com.kingston.mmorpg.game.player.event.PlayerLoginEvent;
import com.kingston.mmorpg.game.scene.actor.Player;
import com.kingston.mmorpg.game.skill.message.RespPlayerSkills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.Collections;

@Controller
@ModuleMeta(module = Modules.SKILL)
public class SkillFacade {

	@Autowired
	private ConfigSkillContainer skillContainer;

	@Subscribe
	public void onPlayerLogin(PlayerLoginEvent loginEvent) {
		Player player = loginEvent.getOwner();
	}

	@Subscribe
	public void onPlayerLevelUp(PlayerLevelUpEvent upEvent) {
		Player player = upEvent.getOwner();
		int nowLevel = 10;

		Collection<ConfigSkill> skills = skillContainer.queryAll();
		skills.stream().filter(configSkill -> configSkill.getNeedLevel() < nowLevel).forEach(configSkill -> {
			System.err.println("玩家升级，学会新技能->" + configSkill.getName());
		});

		System.err.println("角色" + player.getId() + "登录，准备下发技能列表");
		RespPlayerSkills resp = new RespPlayerSkills();
		resp.setSkills(Collections.singletonList(1));
		MessagePusher.pushMessage(player.getId(), resp);
	}

}
