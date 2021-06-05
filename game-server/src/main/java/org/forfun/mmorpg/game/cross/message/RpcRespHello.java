package org.forfun.mmorpg.game.cross.message;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;
import org.forfun.mmorpg.net.rpc.RpcCallbackResponse;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;

@Getter
@Setter
@MessageMeta(cmd = ConstantCross.CMD_RESP_HELLO)
public class RpcRespHello extends RpcCallbackResponse {

    private String content;

    @Override
    public int targetServerId() {
        return 0;
    }
}
