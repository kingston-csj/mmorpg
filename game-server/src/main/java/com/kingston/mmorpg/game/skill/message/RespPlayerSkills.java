package com.kingston.mmorpg.game.skill.message;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.player.service.PlayerService;
import com.kingston.mmorpg.game.skill.service.SkillService;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@MessageMeta(cmd = SkillService.CMD_RESP_SKILLS)
public class RespPlayerSkills extends Message {

    private List<Integer> skills;
}
