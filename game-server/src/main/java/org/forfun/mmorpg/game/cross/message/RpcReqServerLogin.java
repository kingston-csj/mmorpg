package org.forfun.mmorpg.game.cross.message;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;
import org.forfun.mmorpg.net.rpc.RpcMessage;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;

@Getter
@Setter
@MessageMeta(cmd = ConstantCross.CMD_REQ_SERVER_LOGIN)
public class RpcReqServerLogin implements RpcMessage {

    private int targetSid;

    private int fromSid;

    private int serverType;

    /**
     * connection password
     */
    private String sign;

}
