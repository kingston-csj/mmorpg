package org.forfun.mmorpg.game.cross.message;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;

@Getter
@Setter
@MessageMeta(cmd = ConstantCross.CMD_RPC_NODE)
public class RpcServerNode {

    private int sid;

    /**
     * {@link org.forfun.mmorpg.game.ServerType#type}
     */
    private int type;

    private String ip;

    private int port;
}
