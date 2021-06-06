package org.forfun.mmorpg.game.cross.message;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;
import org.forfun.mmorpg.net.rpc.RpcMessage;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;

@Getter
@Setter
@MessageMeta(cmd = ConstantCross.CMD_RESP_SERVER_LOGIN)
public class RpcRespServerLogin implements RpcMessage {

    private int remoteSid;

    private int serverType;

    @Override
    public int targetServerId() {
        return remoteSid;
    }
}
