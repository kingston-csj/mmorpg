package org.forfun.mmorpg.game.skill.message

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf
import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.skill.service.SkillService

@MessageMeta(cmd = SkillService.CMD_RESP_SKILLS)
class RespPlayerSkills : Message {

    @Protobuf
    var skills: MutableList<Int>? = null

    override fun toString(): String {
        return "RespPlayerSkills{skills=$skills}"
    }
}
