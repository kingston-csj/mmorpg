package org.forfun.mmorpg.game.skill.message;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import org.forfun.mmorpg.game.skill.service.SkillService;

import java.util.List;

@MessageMeta(cmd = SkillService.CMD_RESP_SKILLS)
public class RespPlayerSkills implements Message {

    @Protobuf
    private List<Integer> skills;

    public List<Integer> getSkills() {
        return skills;
    }

    public void setSkills(List<Integer> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "RespPlayerSkills{" +
                "skills=" + skills +
                '}';
    }
}
