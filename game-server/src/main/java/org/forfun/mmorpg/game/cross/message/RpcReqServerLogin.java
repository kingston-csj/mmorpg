package org.forfun.mmorpg.game.cross.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import org.forfun.mmorpg.game.MessageSource;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;

@MessageMeta(cmd = ConstantCross.CMD_REQ_SERVER_LOGIN,source = MessageSource.FROM_SERVER_TO_SERVER)
public class RpcReqServerLogin implements Message {

    private int targetSid;

    private int fromSid;

    private int serverType;

    /**
     * connection password
     */
    private String sign;

    public int getTargetSid() {
        return targetSid;
    }

    public void setTargetSid(int targetSid) {
        this.targetSid = targetSid;
    }

    public int getFromSid() {
        return fromSid;
    }

    public void setFromSid(int fromSid) {
        this.fromSid = fromSid;
    }

    public int getServerType() {
        return serverType;
    }

    public void setServerType(int serverType) {
        this.serverType = serverType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
