package org.forfun.mmorpg.game.cross.message;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;
import org.forfun.mmorpg.net.rpc.RpcMessage;
import org.forfun.mmorpg.protocol.annotation.MessageMeta;

import java.util.List;

@Getter
@Setter
@MessageMeta(cmd = ConstantCross.CMD_C2G_FETCH_FIGHT_SERVER_NODES)
public class Rpc_C2G_FetchFightServerNodes implements RpcMessage {

   private List<RpcServerNode> fightNodes;
}
