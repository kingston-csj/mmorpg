package org.forfun.mmorpg.game.skill.facade;

import jforgame.socket.share.annotation.MessageRoute;
import org.forfun.mmorpg.framework.eventbus.Subscribe;
import org.forfun.mmorpg.game.Modules;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.base.MessageUtil;
import org.forfun.mmorpg.game.database.config.domain.ConfigSkill;
import org.forfun.mmorpg.game.database.user.entity.PlayerEnt;
import org.forfun.mmorpg.game.player.event.PlayerLevelUpEvent;
import org.forfun.mmorpg.game.player.event.PlayerLoginEvent;
import org.forfun.mmorpg.game.skill.message.RespPlayerSkills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.Collections;

@Controller
@MessageRoute(module = Modules.SKILL)
public class SkillFacade {

    @Subscribe
    public void onPlayerLogin(PlayerLoginEvent loginEvent) {
        PlayerEnt player = loginEvent.getOwner();
    }

    @Subscribe
    public void onPlayerLevelUp(PlayerLevelUpEvent upEvent) {
        PlayerEnt player = upEvent.getOwner();
        int nowLevel = 10;

        Collection<ConfigSkill> skills = GameContext.getDataManager().queryAll(ConfigSkill.class);
        skills.stream().filter(configSkill -> configSkill.getNeedLevel() < nowLevel).forEach(configSkill -> {
            System.err.println("玩家升级，学会新技能->" + configSkill.getName());
        });

        System.err.println("角色" + player.getId() + "登录，准备下发技能列表");
        RespPlayerSkills resp = new RespPlayerSkills();
        resp.setSkills(Collections.singletonList(1));
        MessageUtil.pushMessage(player.getId(), resp);
    }

}
