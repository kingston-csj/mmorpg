package org.forfun.mmorpg.game.system.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.skill.service.SkillService;
import org.forfun.mmorpg.game.system.PacketId;

@Getter
@Setter
@MessageMeta(cmd = PacketId.RES_ALERT)
public class ResAlert implements Message {

    private int i18nId;

    private String[] params;
}
