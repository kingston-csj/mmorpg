package org.forfun.mmorpg.game.skill.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.forfun.mmorpg.game.skill.service.SkillService;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;
import org.forfun.mmorpg.protocol.Message;

import java.util.List;

@Getter
@Setter
@ToString
@MessageMeta(cmd = SkillService.CMD_RESP_SKILLS)
public class RespPlayerSkills implements Message {

    @Protobuf
    private List<Integer> skills;
}
