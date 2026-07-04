package org.forfun.mmorpg.game.cross.message

import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.MessageSource
import org.forfun.mmorpg.game.cross.constant.ConstantCross

@MessageMeta(cmd = ConstantCross.CMD_G2C_FETCH_FIGHT_SERVER_NODES, source = MessageSource.FROM_SERVER_TO_SERVER)
class Rpc_G2C_FetchFightServerNodes : Message
