package org.forfun.mmorpg.game.cross.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.MessageSource;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;

import java.util.List;

@Getter
@Setter
@MessageMeta(cmd = ConstantCross.CMD_C2G_FETCH_FIGHT_SERVER_NODES, source = MessageSource.FROM_SERVER_TO_SERVER)
public class Rpc_C2G_FetchFightServerNodes implements Message {

   private List<RpcServerNode> fightNodes;
}
