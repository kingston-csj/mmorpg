package org.forfun.mmorpg.game.cross.message

import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.cross.constant.ConstantCross

@MessageMeta(cmd = ConstantCross.CMD_HEART_BEAT)
class RpcHeartBeat : Message
