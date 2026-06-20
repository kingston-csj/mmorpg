package org.forfun.mmorpg.game.system.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import org.forfun.mmorpg.game.skill.service.SkillService;
import org.forfun.mmorpg.game.system.PacketId;

@MessageMeta(cmd = PacketId.RES_ALERT)
public class ResAlert implements Message {

    private int i18nId;

    private String[] params;

    public int getI18nId() {
        return i18nId;
    }

    public void setI18nId(int i18nId) {
        this.i18nId = i18nId;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }
}
