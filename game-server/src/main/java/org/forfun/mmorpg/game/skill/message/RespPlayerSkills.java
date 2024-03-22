package org.forfun.mmorpg.game.skill.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.forfun.mmorpg.game.skill.service.SkillService;

import java.util.List;

@Getter
@Setter
@ToString
@MessageMeta(cmd = SkillService.CMD_RESP_SKILLS)
public class RespPlayerSkills implements Message {

    @Protobuf
    private List<Integer> skills;
}
