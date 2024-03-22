package org.forfun.mmorpg.game.cross.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.MessageSource;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;

@Getter
@Setter
@MessageMeta(cmd = ConstantCross.CMD_REQ_SERVER_LOGIN,source = MessageSource.FROM_SERVER_TO_SERVER)
public class RpcReqServerLogin implements Message {

    private int targetSid;

    private int fromSid;

    private int serverType;

    /**
     * connection password
     */
    private String sign;

}
