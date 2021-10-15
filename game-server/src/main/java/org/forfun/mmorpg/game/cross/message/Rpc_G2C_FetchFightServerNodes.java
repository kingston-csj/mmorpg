package org.forfun.mmorpg.game.cross.message;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;
import org.forfun.mmorpg.net.rpc.RpcMessage;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;

@Getter
@Setter
@MessageMeta(cmd = ConstantCross.CMD_G2C_FETCH_FIGHT_SERVER_NODES)
public class Rpc_G2C_FetchFightServerNodes implements RpcMessage {

}
