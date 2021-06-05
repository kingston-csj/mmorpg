package org.forfun.mmorpg.game.cross.message;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.net.rpc.RpcMessage;

@Getter
@Setter
public class RpcReqServerLogin implements RpcMessage {

    private int targetSid;

    private int fromSid;

    /**
     * connection password
     */
    private String sign;

    @Override
    public int targetServerId() {
        return targetSid;
    }
}
