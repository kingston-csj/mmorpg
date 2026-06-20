package org.forfun.mmorpg.game.cross.message

import jforgame.socket.share.annotation.MessageMeta
import jforgame.socket.share.message.Message
import org.forfun.mmorpg.game.cross.constant.ConstantCross
import java.util.Objects

@MessageMeta(cmd = ConstantCross.CMD_RPC_NODE)
class RpcServerNode : Message {

    var sid: Int = 0

    /**
     * {@link org.forfun.mmorpg.game.ServerType#type}
     */
    var type: Int = 0

    var ip: String? = null
    var port: Int = 0

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as RpcServerNode
        return sid == that.sid && type == that.type && port == that.port && Objects.equals(ip, that.ip)
    }

    override fun hashCode(): Int {
        return Objects.hash(sid, type, ip, port)
    }
}
