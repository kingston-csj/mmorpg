package com.kingston.mmorpg.game.skill.message;

//import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.kingston.mmorpg.framework.net.socket.annotation.MessageMeta;
import com.kingston.mmorpg.framework.net.socket.message.Message;
import com.kingston.mmorpg.game.player.service.PlayerService;
import com.kingston.mmorpg.game.skill.service.SkillService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@MessageMeta(cmd = SkillService.CMD_RESP_SKILLS)
public class RespPlayerSkills extends Message {

//    @Protobuf
    private List<Integer> skills;
}
