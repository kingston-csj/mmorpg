package org.forfun.mmorpg.game.gm.message

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf
import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message

@MessageMeta(cmd = 1)
class ReqGmCommand : Message {

    @Protobuf
    var params: String? = null
}
