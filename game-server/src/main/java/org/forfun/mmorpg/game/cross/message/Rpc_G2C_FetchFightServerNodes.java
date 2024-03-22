package org.forfun.mmorpg.game.cross.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.MessageSource;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;

@Getter
@Setter
@MessageMeta(cmd = ConstantCross.CMD_G2C_FETCH_FIGHT_SERVER_NODES, source = MessageSource.FROM_SERVER_TO_SERVER)
public class Rpc_G2C_FetchFightServerNodes implements Message {

}
