package com.kingston.mmorpg.game.skill.message;

import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.skill.service.SkillService;
import lombok.Data;

import java.util.List;

@MessageMeta(cmd = SkillService.CMD_RESP_SKILLS)
@Data
public class RespPlayerSkills extends Message {

    private List<Integer> skills;
}
