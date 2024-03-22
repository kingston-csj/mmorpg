package org.forfun.mmorpg.game.cross.message;

import jforgame.socket.share.annotation.MessageMeta;
import jforgame.socket.share.message.Message;
import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;

@Getter
@Setter
@MessageMeta(cmd = ConstantCross.CMD_HEART_BEAT)
public class RpcHeartBeat implements Message {
}
