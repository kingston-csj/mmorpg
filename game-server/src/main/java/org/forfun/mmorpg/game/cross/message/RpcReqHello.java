package org.forfun.mmorpg.game.cross.message;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;
import org.forfun.mmorpg.net.rpc.RpcCallbackRequest;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;

@Getter
@Setter
@MessageMeta(cmd = ConstantCross.CMD_REQ_HELLO)
public class RpcReqHello extends RpcCallbackRequest {

    private String content;

    @Override
    public int targetServerId() {
        return 0;
    }
}
